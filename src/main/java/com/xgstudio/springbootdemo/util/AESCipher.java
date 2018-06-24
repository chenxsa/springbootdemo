package com.xgstudio.springbootdemo.util;


import com.xgstudio.springbootdemo.exception.OperateException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * 加密
 * @author chenxsa
 */
public class AESCipher {

    private static final String IV_STRING = "ehGb#er54HBh(s%g";
    private static final String KEY =  "emoSFCy96*g%(Hi2";
    private static final String charset = "UTF-8";

    /**
     * 对字符串加密
     * @param content 目标
     * @return 加密后的内容
     */
    public static String aesEncryptString(String content) {
        byte[] contentBytes = new byte[0];
        try {
            contentBytes = content.getBytes(charset);
            byte[] keyBytes = KEY.getBytes(charset);
            byte[] encryptedBytes = aesEncryptBytes(contentBytes, keyBytes);
            Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(encryptedBytes);
        } catch (Exception e) {
           throw new OperateException("加密错误："+e.getMessage(),e);
        }
    }

    /**
     * 解密
     * @param content
     * @return
     */
    public static String aesDecryptString(String content) {
        try {
            Decoder decoder = Base64.getDecoder();
            byte[] encryptedBytes = decoder.decode(content);
            byte[] keyBytes = KEY.getBytes(charset);
            byte[] decryptedBytes = aesDecryptBytes(encryptedBytes, keyBytes);
            return new String(decryptedBytes, charset);
        } catch (Exception e) {
            throw new OperateException("解密错误："+e.getMessage(), e);
        }
    }

    /**
     * 对二进制加密
     * @param contentBytes
     * @param keyBytes
     * @return
     * @throws Exception
     */
    public static byte[] aesEncryptBytes(byte[] contentBytes, byte[] keyBytes) throws Exception {
        return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE);
    }

    /**
     * 对二进制解密
     * @param contentBytes
     * @param keyBytes
     * @return
     * @throws Exception
     */
    public static byte[] aesDecryptBytes(byte[] contentBytes, byte[] keyBytes) throws Exception {
        return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE);
    }

    private static byte[] cipherOperation(byte[] contentBytes, byte[] keyBytes, int mode) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        byte[] initParam = IV_STRING.getBytes(charset);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, secretKey, ivParameterSpec);

        return cipher.doFinal(contentBytes);
    }

}