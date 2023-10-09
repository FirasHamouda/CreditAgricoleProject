package com.example.creditagricolproject.util

import com.example.creditagricolproject.domaine.model.Account

data class UiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val accounts: List<Account> = emptyList()
)
