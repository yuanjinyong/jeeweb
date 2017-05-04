/**
 * 
 */
package com.jeeweb.platform.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 袁进勇
 *
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private SecurityCacheManager securityCacheManager;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // return securityCacheManager.getSecurityUser(username);

        SecurityUser securityUser = securityCacheManager.getSecurityUser(username);
        SecurityUser curUser = new SecurityUser(securityUser.getUsername(), securityUser.getPassword(),
                securityUser.getAuthorities());
        curUser.setUser(securityUser.getUser());
        return curUser;
    }
}
