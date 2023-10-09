package com.example.creditagricolproject.util

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.creditagricolproject.data.repository.AccountsRepository
import com.example.creditagricolproject.domaine.model.ResponseData
import com.example.creditagricolproject.util.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AccountsRepository
) : ViewModel() {
    val uiState = mutableStateOf(UiState())
    var accountsResponse: ResponseData = ResponseData(emptyList())

    init {
        getAccounts()
    }

    private fun getAccounts() {
        viewModelScope.launch {
            repository.getAccounts().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        uiState.value = UiState(isLoading = true)
                    }

                    is Result.Success -> {
                        accountsResponse = ResponseData(result.data ?: emptyList())
                        val accounts = accountsResponse.data.flatMap { it.accounts }
                        uiState.value = UiState(isLoading = false, accounts = accounts)
                    }

                    is Result.Error -> {
                        uiState.value = UiState(error = result.message)
                    }
                }
            }
        }
    }
}