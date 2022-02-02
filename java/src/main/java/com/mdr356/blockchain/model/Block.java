package com.mdr356.blockchain.model;

public class Block {
    private int index;
    private String timeStamp;
    private int proof;
    private String previous_hash;

    public Block(int index, String timeStamp, int proof, String previous_hash) {
        this.index = index;
        this.timeStamp = timeStamp;
        this.proof = proof;
        this.previous_hash = previous_hash;
    }


    public int getIndex() {
        return index;
    }
    public String getTimeStamp() {
        return timeStamp;
    }
    public int getProof() {
        return proof;
    }
    public String getPrevious_hash() {
        return previous_hash;
    }

}
