package com.security.zarpay.ui.model


const val DUMMY_USER_ID = "user_test_001"

data class FirebaseUser(
    val userId: String = "",
    val name: String = "",
    val initials: String = "",
    val phone: String = "",
    val bankName: String = "",
    val lastFour: String = "",
    val balance: Double = 0.0
)

data class FirebaseTransaction(
    val transactionId: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val name: String = "",
    val amount: Double = 0.0,
    val timestamp: Long = 0L,
    val status: String = "Success",
    val refId: String = ""
)