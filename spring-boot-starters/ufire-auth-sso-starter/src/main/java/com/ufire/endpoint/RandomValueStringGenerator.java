package com.ufire.endpoint;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @title: RandomValueStringGenerator
 * @Author ufiredong
 * @Date: 2021/9/26 14:20
 * @Des:   授权码生成器，直接搬运oauth2.0的
 * @Version 1.0
 */
public class RandomValueStringGenerator {
    private static final char[] DEFAULT_CODEC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private Random random;
    private int length;

    public RandomValueStringGenerator() {
        this(6);
    }

    public RandomValueStringGenerator(int length) {
        this.random = new SecureRandom();
        this.length = length;
    }

    public String generate() {
        byte[] verifierBytes = new byte[this.length];
        this.random.nextBytes(verifierBytes);
        return this.getAuthorizationCodeString(verifierBytes);
    }

    protected String getAuthorizationCodeString(byte[] verifierBytes) {
        char[] chars = new char[verifierBytes.length];

        for(int i = 0; i < verifierBytes.length; ++i) {
            chars[i] = DEFAULT_CODEC[(verifierBytes[i] & 255) % DEFAULT_CODEC.length];
        }

        return new String(chars);
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
