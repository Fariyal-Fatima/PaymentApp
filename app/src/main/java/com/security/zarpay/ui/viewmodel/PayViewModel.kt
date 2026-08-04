package com.security.zarpay.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.security.zarpay.repository.AuthRepository
import com.security.zarpay.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class PaymentState {
    object Idle : PaymentState()
    object Loading : PaymentState()
    object Success : PaymentState()
    data class Error(val message: String) : PaymentState()
}

class PayViewModel : ViewModel() {

    private val firebaseRepository = FirebaseRepository()
    private val authRepository = AuthRepository()

    private val _paymentState = MutableStateFlow<PaymentState>(PaymentState.Idle)
    val paymentState: StateFlow<PaymentState> = _paymentState

    fun sendPayment(receiverName: String, receiverId: String, amount: Double, enteredMpin: String) {
        val senderId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        _paymentState.value = PaymentState.Loading

        viewModelScope.launch {
            // Step 1: Verify MPIN
            val mpinResult = authRepository.verifyMpin(senderId, enteredMpin)

            mpinResult.onSuccess { isValid ->
                if (!isValid) {
                    _paymentState.value = PaymentState.Error("Incorrect MPIN")
                    return@onSuccess
                }

                val sendResult = firebaseRepository.sendMoney(
                    senderId = senderId,
                    receiverId = receiverId,
                    receiverName = receiverName,
                    amount = amount
                )

                sendResult.onSuccess {
                    _paymentState.value = PaymentState.Success
                }.onFailure { e ->
                    _paymentState.value = PaymentState.Error(e.message ?: "Payment failed")
                }

            }.onFailure { e ->
                _paymentState.value = PaymentState.Error(e.message ?: "MPIN verification failed")
            }
        }
    }

    fun resetState() {
        _paymentState.value = PaymentState.Idle
    }
}

