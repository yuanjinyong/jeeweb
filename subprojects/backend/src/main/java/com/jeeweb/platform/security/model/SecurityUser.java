/**
 * 
 */
package com.jeeweb.platform.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.sys.entity.UserEntity;

/**
 * @author 袁进勇
 *
 */
public class SecurityUser extends User {
    private static final long serialVersionUID = -106245041227776233L;

    private UserEntity user; // 系统操作员

    public SecurityUser(UserEntity user, Collection<? extends GrantedAuthority> authorities) {
        this(user.getF_account(), user.getF_password(), authorities);
        this.user = user;
    }

    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String getName() {
        if (user != null) {
            if (!HelpUtil.isEmpty(user.getF_name())) {
                return user.getF_name();
            }
            return user.getF_account();
        }

        return super.getUsername();
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
