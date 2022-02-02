package com.mdr356.blockchain.service;

import com.mdr356.blockchain.model.Block;
import com.mdr356.blockchain.util.HashLib;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ChainService {
    private ArrayList<Block> chain;

    public ChainService() {
        chain = new ArrayList<>();
        // Genesis block
        this.createBlock(1, "0");
    }

    public Block createBlock(int proof, String previous_hash) {
        Block block = new Block(chain.size()+1, LocalDateTime.now().toString(), proof, previous_hash);
        // add block to chain
        chain.add(block);

        return block;
    }

    // get all blockchain
    public ArrayList<Block> getChain() {
        return chain;
    }
    // Getting the last block in the chain, in order to have access to previous hash
    public Block getPreviousBlock() {
        return chain.get(chain.size()-1);
    }

    /*
     * Check if the blockchain is valid
     * 1. check if previous block hash is the same as the current saved previous_hash
     * 2. the proof of each block is correct: which is each block proof shouldn start with 4 leading zeros
     */
    public Boolean isChainValid() {
        Block previousBlock = chain.get(0);
        int blockIndex = 1;
        while(blockIndex < chain.size()) {
            Block block = chain.get(blockIndex);
            // 1. compare current block previousHash with previous block hash
            if (!previousBlock.getPrevious_hash().equals(HashLib.getInstance().sha256(previousBlock))) {
                return false;
            }
            // 2. do the proof contains 4 leading zeroes.
            int previousProof = previousBlock.getProof();
            int proof = block.getProof();

            // do hash operation on proof and it should have 4 leading zeroes
            String hashOperation = HashLib.getInstance().sha256(proof^2-previousProof^2);
            if (!hashOperation.startsWith("0000"))
                return false;

            previousBlock = block;
            blockIndex = blockIndex + 1;
        }

        return true;
    }

    public int proofOfWork(int previousProof) {
        int newProof = 1;
        boolean checkProof = false;

        while(!checkProof) {

            String hashOperation = HashLib.getInstance().sha256(newProof^2-previousProof^2);
            if (hashOperation.startsWith("0000")) {
                checkProof = true;
            } else {
                newProof = newProof + 1;
            }
        }
        return newProof;
    }


}
