package com.ufire.authsso.tools;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AesUtil {

    private AesUtil() {
        throw new UnsupportedOperationException("constrontor cannot be init");
    }

    /**
     * 生成秘钥
     * @return
     */
    public static byte[] generateKey() {

        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES"); // 秘钥生成器
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGen.init(256); // 初始秘钥生成器
        SecretKey secretKey = keyGen.generateKey(); // 生成秘钥
        return secretKey.getEncoded(); // 获取秘钥字节数组
    }

    /**
     * 加密
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key) {

        SecretKey secretKey = new SecretKeySpec(key, "AES"); // 恢复秘钥
        Cipher cipher = null;
        byte[] cipherBytes = null;
        try {
            cipher = Cipher.getInstance("AES"); // 对Cipher完成加密或解密工作
            cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 对Cipher初始化,加密模式
            cipherBytes = cipher.doFinal(data); // 加密数据
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return cipherBytes;
    }

    /**
     * 解密
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key) {

        SecretKey secretKey = new SecretKeySpec(key, "AES"); // 恢复秘钥
        Cipher cipher = null;
        byte[] plainBytes = null;

        try {
            cipher = Cipher.getInstance("AES"); // 对Cipher初始化,加密模式
            cipher.init(Cipher.DECRYPT_MODE, secretKey); // 对Cipher初始化,加密模式
            plainBytes = cipher.doFinal(data); // 解密数据
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return plainBytes;
    }
}