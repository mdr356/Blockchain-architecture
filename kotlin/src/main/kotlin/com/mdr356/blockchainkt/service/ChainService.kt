package com.mdr356.blockchainkt.service

import com.mdr356.blockchainkt.model.Block
import com.mdr356.blockchainkt.model.Transaction
import com.mdr356.blockchainkt.util.HashLib
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


@Service
class ChainService {

    // initialize chain
    private var chain : ArrayList<Block> = ArrayList()

    init {
        // create genesis block
        createBlock(1.0, "0", arrayListOf())
    }
    /*
     * create new block include proof and previousHash
     * add block to chain
     * return block
     */
    fun createBlock(
        proof: Double,
        previousHash: String,
        transactions: ArrayList<Transaction>
    ): Block {
        val block =  Block(
            index = chain.size + 1,
            timeStamp = LocalDateTime.now().toString(),
            proof = proof,
            previousHash = previousHash,
            transactions = transactions
        )

        // add new block to chain
        chain.add(block)

        return block
    }

    fun getChain() : ArrayList<Block> {
        return chain
    }

    fun getPreviousBlock() : Block {
        return chain[chain.size-1]
    }
    /*
     * iterate through chain, and see previousHash is different then previous block hash
     * it it is, then invalid.
     * also check to see if thje previousHash starts with four leading zeroes.
     */
    fun isChainValid() : Boolean {
        var previousBlock = chain[0]
        var blockIndex = 1

        while (blockIndex < chain.size) {
            var block = chain[blockIndex]
            if (block.previousHash != HashLib.sha256(previousBlock)) {
                return false
            }

            val previousProof = previousBlock.proof
            val proof = block.proof
            val hashOperation = HashLib.sha256(Math.sqrt(proof) - Math.sqrt(previousProof))

            if (hashOperation.subSequence(0,4) != "0000") {
                return false
            }

            previousBlock = block
            blockIndex += 1
        }
        return true
    }

    /*
     * include the current proof along with the previous proof
     * find the new hash that beings with four leading zeros
     */
    fun proofOfWork(previousProof: Double) : Double {
        var newProof = 1.0
        var checkProof = false

        while (!checkProof) {
            var hashOperation = HashLib.sha256(Math.sqrt(newProof) - Math.sqrt(previousProof))
            if (hashOperation.subSequence(0,4) == "0000") {
                checkProof = true
            } else {
                newProof += 1
            }
        }
        return newProof
    }
}