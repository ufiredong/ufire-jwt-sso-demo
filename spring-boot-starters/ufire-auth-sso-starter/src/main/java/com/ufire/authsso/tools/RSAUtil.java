package com.ufire.authsso.tools;

import org.springframework.util.ResourceUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @title: RSAUtil
 * @Author ufiredong
 * @Date: 2021/10/11 9:23
 * @Des:
 * @Version 1.0
 */
public class RSAUtil {

    public static final BASE64Encoder ENCODER = new BASE64Encoder();
    public static final BASE64Decoder DECODER = new BASE64Decoder();


    /**
     * 获取公钥
     *
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String filePath) throws Exception {
        FileReader fr = new FileReader(ResourceUtils.getFile(filePath));
        BufferedReader br = new BufferedReader(fr);
        StringBuilder keyString = new StringBuilder();
        String str;
        while ((str = br.readLine()) != null) {
            keyString.append(str);
        }
        br.close();
        fr.close();
        byte[] keyBytes = DECODER.decodeBuffer(keyString.toString());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 获取密钥
     *
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String filename) throws Exception {
        File file = ResourceUtils.getFile(filename);
        FileReader fr = new FileReader(ResourceUtils.getFile(filename));
        BufferedReader br = new BufferedReader(fr);
        StringBuilder keyString = new StringBuilder();
        String str;
        while ((str = br.readLine()) != null) {
            keyString.append(str);
        }
        br.close();
        fr.close();
        byte[] keyBytes = DECODER.decodeBuffer(keyString.toString());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    /**
     * 根据密文，生存rsa公钥和私钥,并写入指定文件
     *
     * @param secret 生成密钥的密文
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static void generateKey(String dir, String secret) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        // 公钥
        PublicKey publicKey = keyPair.getPublic();
        // 私钥
        PrivateKey privateKey = keyPair.getPrivate();
        //得到公钥字符串
        String publicKeyString = ENCODER.encode(publicKey.getEncoded());
        //得到私钥字符串
        String privateKeyString = ENCODER.encode(privateKey.getEncoded());
        //将密钥对写入到文件
        FileWriter pubfw = new FileWriter(dir + "rsa-jwt.pubkey");
        FileWriter prifw = new FileWriter(dir + "rsa-jwt.prikey");
        BufferedWriter pubbw = new BufferedWriter(pubfw);
        BufferedWriter pribw = new BufferedWriter(prifw);
        pubbw.write(publicKeyString);
        pribw.write(privateKeyString);
        pubbw.flush();
        pubbw.close();
        pubfw.close();
        pribw.flush();
        pribw.close();
        prifw.close();
        System.out.println("公钥生成成功!公钥文件为：" + dir + "rsa-jwt.pubkey");
        System.out.println("私钥生成成功!私钥文件为：" + dir + "rsa-jwt.prikey");
    }

    private static byte[] readFile(String fileName) throws Exception {
        return Files.readAllBytes(ResourceUtils.getFile(fileName).toPath());
    }

    private static void writeFile(String destPath, byte[] bytes) throws IOException {
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);
    }

    public static void main(String[] args) throws Exception {
        RSAUtil.generateKey("", "sc@Login(Auth}*^31)&taoqz%");
    }
}
