package com.example.creditagricolproject.domaine.model

data class AccountsResponse(
    val accounts: List<Account>,
    val isCA: Int,
    val name: String
)