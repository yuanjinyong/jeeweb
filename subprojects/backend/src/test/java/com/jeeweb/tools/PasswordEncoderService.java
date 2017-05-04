/**
 * 
 */
package com.jeeweb.tools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 袁进勇
 *
 */
public class PasswordEncoderService {
    /**
     * @param args
     */
    public static void main(String[] args) {
        String plaintext = "12345678";
        System.out.println("明文：" + plaintext + "，密文：" + new BCryptPasswordEncoder().encode(plaintext));
    }
}
