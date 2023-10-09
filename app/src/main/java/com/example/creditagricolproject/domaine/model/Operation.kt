package com.example.creditagricolproject.domaine.model
import java.io.Serializable

data class Operation(
    val amount: String,
    val category: String,
    val date: String,
    val id: String,
    val title: String
) : Serializable
