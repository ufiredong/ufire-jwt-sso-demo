package com.ufire.authsso.tools;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.ResourceUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
 * @title: RSAUtil
 * @Author ufiredong
 * @Date: 2021/10/11 9:23
 * @Des:
 * @Version 1.0
 */
public class RSAUtil {

    private static String algorithm = "RSA";

    public static final BASE64Encoder ENCODER = new BASE64Encoder();
    public static final BASE64Decoder DECODER = new BASE64Decoder();


    /**
     * 获取公钥
     *
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        // 对key进行base64加密
        byte[] decode = Base64.getMimeDecoder().decode(key.replace("\n", ""));
        //公钥使用X509进行加密
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decode);
        //加密方式RSA
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        //生成publicKey
        return keyFactory.generatePublic(spec);
    }

    /**
     * 获取密钥
     *
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        //对key进行base64加密
        byte[] decode = Base64.getDecoder().decode(key.replace("\n", ""));
        //使用PKCS8进行加密
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decode);
        //加密方式RSA
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        //生成privateKey
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
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
        keyPairGenerator.initialize(2048, secureRandom);
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

    public static String getFileValue(String path) throws IOException {
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + path);
        StringBuffer sb = new StringBuffer();
        RSAUtil.readToBuffer(sb, file.toPath().toString());
        return sb.toString();
    }

    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }

    public static void main(String[] args) throws Exception {
       generateKey("","ufiredong");
    }
}
