package com.security.zarpay.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.security.zarpay.ui.model.FirebaseUser
import com.security.zarpay.ui.model.FirebaseTransaction
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import com.google.firebase.database.ServerValue
class FirebaseRepository {

    private val database = FirebaseDatabase.getInstance("https://zarpay-91a2e-default-rtdb.asia-southeast1.firebasedatabase.app").reference

    // Real-time listener for user data (balance, name, etc.)
    fun getUserData(userId: String): Flow<FirebaseUser?> = callbackFlow {
        val ref = database.child("users").child(userId)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(FirebaseUser::class.java)
                trySend(user)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    // Real-time listener for transactions list
    fun getTransactions(userId: String): Flow<List<FirebaseTransaction>> = callbackFlow {
        val ref = database.child("transactions").child(userId)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.children.mapNotNull {
                    it.getValue(FirebaseTransaction::class.java)
                }.sortedByDescending { it.timestamp }
                trySend(list)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    // Send money — atomic-ish balance update + transaction record
    class InsufficientBalanceException : Exception("Insufficient balance")

    // class ke andar
    suspend fun sendMoney(
        senderId: String,
        receiverId: String,
        receiverName: String,
        amount: Long          // paise
    ): Result<Unit> {
        return try {
            require(amount > 0) { "Invalid amount" }
            require(senderId != receiverId) { "You cannot send money to yourself" }

            val senderSnapshot = database.child("users").child(senderId).get().await()
            val receiverSnapshot = database.child("users").child(receiverId).get().await()

            if (!receiverSnapshot.exists()) {
                return Result.failure(Exception("Receiver not found"))
            }

            val senderBalance = senderSnapshot.child("balance").getValue(Long::class.java) ?: 0L
            if (senderBalance < amount) {
                return Result.failure(InsufficientBalanceException())
            }

            val senderName = senderSnapshot.child("name").getValue(String::class.java) ?: ""
            val transactionId = UUID.randomUUID().toString()
            val refId = "UTR${(100000..999999).random()}"

            // Map use kiya kyunki ServerValue.TIMESTAMP model ke Long field mein nahi jaa sakta
            fun txnMap(name: String, signedAmount: Long) = mapOf(
                "transactionId" to transactionId,
                "senderId" to senderId,
                "receiverId" to receiverId,
                "name" to name,
                "amount" to signedAmount,
                "timestamp" to ServerValue.TIMESTAMP,
                "status" to "Success",
                "refId" to refId
            )

            // Saare paths ek saath: ya sab likhe jaate hain, ya kuch bhi nahi
            val updates = hashMapOf<String, Any>(
                "users/$senderId/balance" to ServerValue.increment(-amount),
                "users/$receiverId/balance" to ServerValue.increment(amount),
                "transactions/$senderId/$transactionId" to txnMap(receiverName, -amount),
                "transactions/$receiverId/$transactionId" to txnMap(senderName, amount)
            )

            database.updateChildren(updates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    }


