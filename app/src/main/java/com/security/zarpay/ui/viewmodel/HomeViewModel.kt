package com.security.zarpay.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.security.zarpay.repository.FirebaseRepository
import com.security.zarpay.ui.model.FirebaseUser
import com.security.zarpay.ui.model.FirebaseTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class HomeUiState(
    val user: FirebaseUser? = null,
    val transactions: List<FirebaseTransaction> = emptyList(),
    val isLoading: Boolean = true
)

class HomeViewModel : ViewModel() {

    private val repository = FirebaseRepository()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun loadData(userId: String) {
        viewModelScope.launch {
            combine(
                repository.getUserData(userId),
                repository.getTransactions(userId)
            ) { user, transactions ->
                HomeUiState(
                    user = user,
                    transactions = transactions,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}

