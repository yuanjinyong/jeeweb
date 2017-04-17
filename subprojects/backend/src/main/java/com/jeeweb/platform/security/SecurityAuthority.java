/**
 * 
 */
package com.jeeweb.platform.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author 袁进勇
 *
 */
public class SecurityAuthority implements ConfigAttribute, GrantedAuthority {
    public static final String UNCONFIGURED_AUTHORITY = "UNCONFIGURED_AUTHORITY";

    private static final long serialVersionUID = -4869163091587715770L;
    private final String authority;

    public SecurityAuthority(String authority) {
        Assert.hasText(authority, "You must provide a authority");
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String getAttribute() {
        return authority;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SecurityAuthority) {
            return authority.equals(((SecurityAuthority) obj).authority);
        }

        return false;
    }

    public int hashCode() {
        return this.authority.hashCode();
    }

    public String toString() {
        return this.authority;
    }

    public static List<ConfigAttribute> createListFromCommaDelimitedString(String access) {
        return createList(StringUtils.commaDelimitedListToStringArray(access));
    }

    public static List<ConfigAttribute> createList(String... attributeNames) {
        Assert.notNull(attributeNames, "You must supply an array of attribute names");
        List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>(attributeNames.length);

        for (String attribute : attributeNames) {
            attributes.add(new SecurityAuthority(attribute.trim()));
        }

        return attributes;
    }
}
