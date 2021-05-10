package com.example.financemanager.model

import java.util.*
import java.io.Serializable

data class Transaction(
        val place: String,
        val category: String,
        val amount: Float,
        val date: Date
) : Serializable
