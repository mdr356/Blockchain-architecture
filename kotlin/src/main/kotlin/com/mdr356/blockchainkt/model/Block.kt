package com.mdr356.blockchainkt.model

data class Block(
    val index: Int,
    val timeStamp: String,
    val proof: Double,
    val previousHash: String,
    val transactions: ArrayList<Transaction>,
)
