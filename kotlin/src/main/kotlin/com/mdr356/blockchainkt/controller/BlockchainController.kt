package com.mdr356.blockchainkt.controller

import com.mdr356.blockchainkt.model.Block
import com.mdr356.blockchainkt.service.ChainService
import com.mdr356.blockchainkt.util.HashLib
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BlockchainController {

    @Autowired
    lateinit var blockChain: ChainService

    /*
     * get a complete copy of the block chain
     */
    @GetMapping(value = arrayOf("/get_chain"))
    fun getChain() : ResponseEntity<ArrayList<Block>> {
        return ResponseEntity(blockChain.getChain(), HttpStatus.OK)
    }

    @GetMapping(value = arrayOf("/is_valid"))
    fun isValid() : ResponseEntity<String> {
        val isValid = blockChain.isChainValid()
        return if (isValid) {
            ResponseEntity("All good. The blockchain is valid", HttpStatus.OK)
        } else {
            ResponseEntity("Blockchain is not valid", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    @GetMapping(value = arrayOf("/mine_block"))
    fun mineBlock(): ResponseEntity<Block> {
        val previousBlock = blockChain.getPreviousBlock()
        val previousProof = previousBlock.proof
        // current proof
        val proof = blockChain.proofOfWork(previousProof)

        // get previous hash in order to add to current block field.
        // previous hash is the previous block hash 256.

        // get previous hash in order to add to current block field.
        // previous hash is the previous block hash 256.
        val previousHash: String = HashLib.sha256(previousBlock)

        // create block
        val block = blockChain.createBlock(proof, previousHash)

        return ResponseEntity(block, HttpStatus.OK)
    }

}