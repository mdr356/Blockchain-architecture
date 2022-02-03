package com.mdr356.blockchain.controller;

import com.mdr356.blockchain.model.Block;
import com.mdr356.blockchain.service.ChainService;
import com.mdr356.blockchain.util.HashLib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class ChainController {

    @Autowired
    ChainService blockChain;
    /*
     * get a complete copy of the block chain
     */
    @GetMapping(value = "/get_chain")
    public ResponseEntity<ArrayList<Block>> getChain() {
        return new ResponseEntity<>(blockChain.getChain(), HttpStatus.OK);
    }

    /*
     * is chain valid
     */
    @GetMapping(value = "/is_valid")
    public ResponseEntity<String> isValid() {
        boolean isValid = blockChain.isChainValid();
        if (isValid) {
            return new ResponseEntity<>("All good. The blockchain is valid.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Blockchain is not valid.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "/mine_block")
    public ResponseEntity<Block> mineBlock() {
        Block previousBlock = blockChain.getPreviousBlock();
        int previousProof = previousBlock.getProof();
        // do proof of work for new block.
        int proof = blockChain.proofOfWork(previousProof);

        // get previous hash in order to add to current block field.
        // previous hash is the previous block hash 256.
        String previousHash = HashLib.getInstance().sha256(previousBlock);

        // create block
        Block block = blockChain.createBlock(proof, previousHash);

        // clear transactions
        return new ResponseEntity<>(block, HttpStatus.OK);
    }

}
