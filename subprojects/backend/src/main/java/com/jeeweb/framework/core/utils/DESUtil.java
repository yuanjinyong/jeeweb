/**
 * 
 */
package com.jeeweb.framework.core.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.xml.bind.DatatypeConverter;

import com.jeeweb.framework.core.exception.BusinessException;

/**
 * @author 袁进勇
 *
 */
public class DESUtil {
    private static Key key;

    static {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed("Zhuku@2017".getBytes());
            generator.init(secureRandom);
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            throw new RuntimeException("创建秘钥失败！", e);
        }
    }

    /**
     * 对字符串进行加密，返回BASE64的加密字符串
     * 
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        try {
            byte[] strBytes = str.getBytes("UTF-8");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptStrBytes = cipher.doFinal(strBytes);
            return DatatypeConverter.printBase64Binary(encryptStrBytes);
        } catch (Exception e) {
            throw new BusinessException("字符串加密失败！", e);
        }
    }

    /**
     * 对BASE64加密字符串进行解密
     * 
     * @param str
     * @return
     */
    public static String decrypt(String str) {
        try {
            byte[] strBytes = DatatypeConverter.parseBase64Binary(str);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptStrBytes = cipher.doFinal(strBytes);
            return new String(encryptStrBytes, "UTF-8");
        } catch (Exception e) {
            throw new BusinessException("字符串解密失败！", e);
        }
    }
}
