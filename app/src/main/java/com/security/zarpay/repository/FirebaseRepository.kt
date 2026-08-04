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
    suspend fun sendMoney(
        senderId: String,
        receiverId: String,
        receiverName: String,
        amount: Double
    ): Result<Unit> {
        return try {
            val senderSnapshot = database.child("users").child(senderId).get().await()
            val senderBalance = senderSnapshot.child("balance").getValue(Double::class.java) ?: 0.0

            if (senderBalance < amount) {
                return Result.failure(Exception("Insufficient balance"))
            }

            val receiverSnapshot = database.child("users").child(receiverId).get().await()
            val receiverBalance = receiverSnapshot.child("balance").getValue(Double::class.java) ?: 0.0

            // Update balances
            database.child("users").child(senderId).child("balance").setValue(senderBalance - amount).await()
            database.child("users").child(receiverId).child("balance").setValue(receiverBalance + amount).await()

            val transactionId = UUID.randomUUID().toString()
            val refId = "UTR${(100000..999999).random()}"
            val timestamp = System.currentTimeMillis()

            // Record for sender (debit)
            val senderTxn = FirebaseTransaction(
                transactionId = transactionId,
                senderId = senderId,
                receiverId = receiverId,
                name = receiverName,
                amount = -amount,
                timestamp = timestamp,
                status = "Success",
                refId = refId
            )
            database.child("transactions").child(senderId).child(transactionId).setValue(senderTxn).await()

            // Record for receiver (credit)
            val receiverName2 = senderSnapshot.child("name").getValue(String::class.java) ?: ""
            val receiverTxn = FirebaseTransaction(
                transactionId = transactionId,
                senderId = senderId,
                receiverId = receiverId,
                name = receiverName2,
                amount = amount,
                timestamp = timestamp,
                status = "Success",
                refId = refId
            )
            database.child("transactions").child(receiverId).child(transactionId).setValue(receiverTxn).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

