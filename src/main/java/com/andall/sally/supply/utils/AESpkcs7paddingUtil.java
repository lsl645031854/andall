package com.andall.sally.supply.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 11:57 2020/8/11
 */
public class AESpkcs7paddingUtil {
    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     * Java 6支持PKCS5Padding填充方式
     * Bouncy Castle支持PKCS7Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

    static {
        //如果是PKCS7Padding填充方式，则必须加上下面这行
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 生成密钥
     * @return	密钥
     * @throws Exception
     */
    public static String generateKey() throws Exception {
        //实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        /**
         * 设置AES密钥长度
         * AES要求密钥长度为128位或192位或256位，java默认限制AES密钥长度最多128位
         * 如需192位或256位，则需要到oracle官网找到对应版本的jdk下载页，在"Additional Resources"中找到
         * "Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files",点击[DOWNLOAD]下载
         * 将下载后的local_policy.jar和US_export_policy.jar放到jdk安装目录下的jre/lib/security/目录下，替换该目录下的同名文件
         *
         * SecureRandom 详解
         * https://www.cnblogs.com/deng-cc/p/8064481.html
         */

//        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
//        secureRandom.setSeed("321".getBytes());
//        kg.init(128, secureRandom);
        kg.init(128, new SecureRandom("321".getBytes()));
        //生成密钥
        SecretKey secretKey = kg.generateKey();
        //获得密钥的字符串形式
        return Base64.encodeBase64String(secretKey.getEncoded());
    }

    /**
     * AES加密
     * @param source	源字符串
     * @param key	密钥
     * @return	加密后的密文
     * @throws Exception
     */
    public static String encrypt(String source, String key) throws Exception {
        byte[] sourceBytes = source.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = Base64.decodeBase64(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM));
        byte[] decrypted = cipher.doFinal(sourceBytes);
        return Base64.encodeBase64String(decrypted);
    }

    /**
     * AES解密
     * @param encryptStr	加密后的密文
     * @param key	密钥
     * @return	源字符串
     * @throws Exception
     */
    public static String decrypt(String encryptStr, String key) throws Exception {
        byte[] sourceBytes = Base64.decodeBase64(encryptStr);
        byte[] keyBytes = Base64.decodeBase64(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM));
        byte[] decoded = cipher.doFinal(sourceBytes);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        String str = "{\n" + "\"userId\": 20786,\n" + "\"username\": \"测试账号\",\n" + "\"userhead\": \"2706.00\",\n" + "\"clientId\": \"123\",\n" + "\"clientSecret\": \"321\",\n" + "\"subDate\": \"2020-05-30 17:15:19\"\n" + "}";
        String s = generateKey();
        System.out.println(s);
        String encrypt = encrypt(str,  Base64.encodeBase64String("m9UM!pNbj^q_xxKa".getBytes()));
        System.out.println("加密 : " + encrypt);

        String decrypt = decrypt(encrypt, Base64.encodeBase64String("m9UM!pNbj^q_xxKa".getBytes()));
        System.out.println("解密 ：" + decrypt);
    }

}
