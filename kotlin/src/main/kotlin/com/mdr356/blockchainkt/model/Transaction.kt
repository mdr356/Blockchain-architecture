package com.mdr356.blockchainkt.model

data class Transaction (
    val sender: String,
    val receiver: String,
    val amount: Double,
)
