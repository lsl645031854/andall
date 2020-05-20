package com.andall.sally.supply.hutool;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import org.junit.Test;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 16:01 2020/9/4
 */
public class HuToolCryptoTest {

    @Test
    public void test() {
        String md5 = DigestUtil.md5Hex("测试md5");
        System.out.println(md5);
    }

    @Test
    public void testAES() {
        String str = "{\n" + "\"userId\": 20786,\n" + "\"username\": \"测试账号\",\n" + "\"userhead\": \"2706.00\",\n" + "\"clientId\": \"123\",\n" + "\"clientSecret\": \"321\",\n" + "\"subDate\": \"2020-05-30 17:15:19\"\n" + "}";
        AES aes = new AES(Mode.CTS, Padding.PKCS5Padding, "m9UM!pNbj^q_xxKa".getBytes(), "0102030405060708".getBytes());
        // 加密为16进制表示
        String encryptHex = aes.encryptHex(str);
        System.out.println(encryptHex);

        // 解密为字符串
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println(decryptStr);
    }


    @Test
    public void testAES2() {
        String str = "{\n" + "\"userId\": 20786,\n" + "\"username\": \"测试账号\",\n" + "\"userhead\": \"2706.00\",\n" + "\"clientId\": \"123\",\n" + "\"clientSecret\": \"321\",\n" + "\"subDate\": \"2020-05-30 17:15:19\"\n" + "}";
        // 构建
        AES aes = SecureUtil.aes("m9UM!pNbj^q_xxKa".getBytes());

        // 加密
        byte[] encrypt = aes.encrypt(str);
        // 解密
        byte[] decrypt = aes.decrypt(encrypt);

        // 加密为16进制表示
        String encryptHex = aes.encryptHex(str);
        // 解密为字符串
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println(decryptStr);
    }

}
