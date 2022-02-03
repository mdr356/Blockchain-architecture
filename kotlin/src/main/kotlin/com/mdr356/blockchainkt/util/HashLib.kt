package com.mdr356.blockchainkt.util

import com.mdr356.blockchainkt.model.Block
import java.lang.Double.toHexString
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object HashLib {
    private val digest: MessageDigest = MessageDigest.getInstance("SHA-256")

    fun sha256(block: Block) : String {
        val encodedhash : ByteArray = digest.digest(
            block.toString().toByteArray(StandardCharsets.UTF_8)
        )
        return bytesToHex(encodedhash)
    }

    fun sha256(proof: Double) : String {
        val encodedhash : ByteArray = digest.digest(
            proof.toString().toByteArray(StandardCharsets.UTF_8)
        )
        return bytesToHex(encodedhash)

    }

    private fun bytesToHex(hash: ByteArray): String {
        val hexString = StringBuilder(2 * hash.size)
        for (element in hash) {
            val hex = Integer.toHexString(0xff and element.toInt())
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }
        return hexString.toString()
    }
}