/**
 * 
 */
package com.jeeweb.tools;

import com.jeeweb.framework.core.utils.BASE64Util;

/**
 * @author 袁进勇
 *
 */
public class BasicAuthenticationService {
    /**
     * @param args
     */
    public static void main(String[] args) {
        String f_account = "yuanjinyong";
        String f_password = "yuanjinyong";
        System.out.println("明文：[" + f_account + ":" + f_password + "]，密文：[Basic "
                + BASE64Util.encode(f_account + ":" + f_password) + "]");
    }
}
