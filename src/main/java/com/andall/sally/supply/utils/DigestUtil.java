package com.andall.sally.supply.utils;

import lombok.extern.log4j.Log4j2;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @author: LN
 * @date: 2018/11/29 09:26
 * @description:
 */
@Log4j2
public class DigestUtil {
    /**
     * 加密
     * 1.构造密钥生成器
     * 2.根据ecnodeRules规则初始化密钥生成器
     * 3.产生密钥
     * 4.创建和初始化密码器
     * 5.内容加密
     * 6.返回字符串
     */
    public static String DES = "AES"; // optional value AES/DES/DESede

    public static String CIPHER_ALGORITHM = "AES"; // optional value AES/DES/DESede


    public static Key getKey(String strKey) {
        try {
            if (strKey == null) {
                strKey = "";
            }
            KeyGenerator _generator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            _generator.init(128, secureRandom);
            return _generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(" 初始化密钥出现异常 ");
        }
    }

    public static String AESEncode(String key, String data) {
        try {
            SecureRandom sr = new SecureRandom();
            Key secureKey = getKey(key);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, sr);
            byte[] bt = cipher.doFinal(data.getBytes());
            String strS = new BASE64Encoder().encode(bt);
            return strS;
        }catch (Exception e){
            log.error("AES解密失败");
        }

        return null;
    }


    public static String AESDncode(String key,String message) {
        try {
            SecureRandom sr = new SecureRandom();
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            Key secureKey = getKey(key);
            cipher.init(Cipher.DECRYPT_MODE, secureKey, sr);
            byte[] res = new BASE64Decoder().decodeBuffer(message);
            res = cipher.doFinal(res);
            return new String(res);
        }catch (Exception e){
            log.error("AES解密失败");
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        String message = "15738825604";
        String key = "uyKjdY";
        String encryptMsg = AESEncode(key, message);
        System.out.println("encrypted message is below :");
        System.out.println(encryptMsg);

        String decryptedMsg = AESDncode(key, encryptMsg);
        System.out.println("decrypted message is below :");
        System.out.println(decryptedMsg);
    }
}
