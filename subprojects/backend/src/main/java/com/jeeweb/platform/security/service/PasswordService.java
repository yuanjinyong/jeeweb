/**
 * 
 */
package com.jeeweb.platform.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jeeweb.framework.core.utils.HelpUtil;

/**
 * @author 袁进勇
 *
 */
@Component
public class PasswordService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String generatePassword() {
        return encodePassword("12345678"); // TODO 这里需要改成随机密码，然后通过短信或者通知的方式通知用户
    }

    public String encodePassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public Boolean validatePassword(String plainPassword, String cipherPassword) {
        return !HelpUtil.isEmpty(plainPassword) && passwordEncoder.matches(plainPassword, cipherPassword);
    }
}
