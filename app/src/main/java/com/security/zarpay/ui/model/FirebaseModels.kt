package com.security.zarpay.ui.model


const val DUMMY_USER_ID = "user_test_001"

data class FirebaseUser(
    val userId: String = "",
    val name: String = "",
    val initials: String = "",
    val phone: String = "",
    val bankName: String = "",
    val lastFour: String = "",
    val balance: Long = 0L
)

data class FirebaseTransaction(
    val transactionId: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val name: String = "",
    val amount: Long = 0L,
    val timestamp: Long = 0L,
    val status: String = "Success",
    val refId: String = ""
)