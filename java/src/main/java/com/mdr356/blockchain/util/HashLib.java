package com.mdr356.blockchain.util;

import com.google.gson.Gson;
import com.mdr356.blockchain.model.Block;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 * Singleton class
 */
public class HashLib {
    // The filed must be declared volatie so that doulble check lock would work
    // correctly
    private static volatile HashLib instance;
    public static HashLib getInstance() {
        HashLib result = instance;
        if (result != null) {
            return result;
        }

        synchronized (HashLib.class) {
            if (instance == null) {
                instance = new HashLib();
            }
            return instance;
        }
    }

    private MessageDigest digest;
    private HashLib() {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String sha256(Block previousBlock)  {
        byte[] encodedhash = digest.digest(previousBlock.toString().getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    public String sha256(int proof) {
        byte[] encodedhash = digest.digest(Integer.toString(proof).getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff * hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
