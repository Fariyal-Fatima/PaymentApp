package com.security.zarpay.repository
import com.security.zarpay.ui.model.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.mindrot.jbcrypt.BCrypt
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance("https://zarpay-91a2e-default-rtdb.asia-southeast1.firebasedatabase.app").reference

    // Signup with Email, Password, Name, and MPIN
    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        mpin: String
    ): Result<String> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: throw Exception("User ID not found")

            val initials = name.trim().split(" ")
                .mapNotNull { it.firstOrNull()?.uppercase() }
                .take(2)
                .joinToString("")

            val newUser = FirebaseUser(
                userId = userId,
                name = name,
                initials = initials,
                phone = "",
                bankName = "SBI",
                lastFour = (1111..9999).random().toString(),
                balance = 5_000_000L
            )
            database.child("users")
                .child(userId)
                .setValue(newUser)
                .await()


// Hash MPIN before storing (bcrypt with auto-generated salt)
            val hashedMpin = BCrypt.hashpw(mpin, BCrypt.gensalt())
            database.child("mpins").child(userId).setValue(hashedMpin).await()


            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Login with Email and Password
    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: throw Exception("User ID not found")
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Verify MPIN before transaction
    suspend fun verifyMpin(userId: String, enteredMpin: String): Result<Boolean> {
        return try {
            val snapshot = database.child("mpins").child(userId).get().await()
            val savedHashedMpin = snapshot.getValue(String::class.java) ?: ""
            val isValid = BCrypt.checkpw(enteredMpin, savedHashedMpin)
            Result.success(isValid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Check if user already logged in
    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    // Logout
    fun logout() {
        auth.signOut()
    }
}


