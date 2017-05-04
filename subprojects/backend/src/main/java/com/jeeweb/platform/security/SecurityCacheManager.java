/**
 * 
 */
package com.jeeweb.platform.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.UrlPathHelper;

import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.platform.security.utils.RequestMappingInfoUtil;
import com.jeeweb.platform.sys.entity.MenuEntity;
import com.jeeweb.platform.sys.entity.UrlEntity;
import com.jeeweb.platform.sys.entity.UserEntity;
import com.jeeweb.platform.sys.mapper.MenuUrlMapper;
import com.jeeweb.platform.sys.mapper.UserMapper;
import com.jeeweb.platform.sys.mapper.UserMenuMapper;

/**
 * @author 袁进勇
 *
 */
@Component
public class SecurityCacheManager {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityCacheManager.class);

    // 权限缓存
    private static Map<String, SecurityAuthority> authoritiesCache = new HashMap<String, SecurityAuthority>();
    // URL配置的权限缓存
    private static Map<String, Collection<ConfigAttribute>> urlAuthoritiesCache = new HashMap<String, Collection<ConfigAttribute>>();
    // 后台URL地址如果未配置访问权限
    private static Collection<ConfigAttribute> unconfigAuthority = new ArrayList<ConfigAttribute>();
    // 操作员缓存，采用集群部署时，不能使用下面的单机版缓存。
    private static Map<String, SecurityUser> securityUserCache = new HashMap<String, SecurityUser>();

    private UrlPathHelper urlPathHelper = new UrlPathHelper();
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private MenuUrlMapper menuUrlMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserMenuMapper userMenuMapper;

    static {
        unconfigAuthority.add(new SecurityAuthority(SecurityAuthority.UNCONFIGURED_AUTHORITY));
    }

    public void loadCache(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        loadUrlAuthoritiesCache();
    }

    public void loadUrlAuthoritiesCache() {
        List<RowMap> menuUrlMapList = menuUrlMapper
                .selectMapEntityListPage(new ParameterMap("f_status", 1).setOrderBy("f_patterns, f_methods"));

        urlAuthoritiesCache.clear();

        // 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
        for (RowMap menuUrlMap : menuUrlMapList) {
            String urlId = menuUrlMap.getString("f_url_id");
            Collection<ConfigAttribute> authorityList = urlAuthoritiesCache.get(urlId);
            if (authorityList == null) {
                authorityList = new ArrayList<ConfigAttribute>();
                urlAuthoritiesCache.put(urlId, authorityList);
            }

            authorityList.add(getAuthority(menuUrlMap.getString("f_menu_id")));
        }
    }

    public void reloadSecurityUserCache() {
        long startTime = System.currentTimeMillis();
        LOG.info("========刷新操作员信息开始========");

        for (Map.Entry<String, SecurityUser> entry : securityUserCache.entrySet()) {
            UserEntity user = userMapper.selectUserByAccount(entry.getKey());
            if (user != null && user.getF_is_can_login() == 1) {
                securityUserCache.put(user.getF_account(), new SecurityUser(user, getUserAuthorities(user)));
            } else {
                securityUserCache.remove(entry.getKey());
            }
        }

        LOG.info("========刷新操作员信息完成，耗时：{}ms========", (System.currentTimeMillis() - startTime));
    }

    public void reloadSecurityUserCache(UserEntity user) {
        String f_account = user.getF_account();
        long startTime = System.currentTimeMillis();
        LOG.info("========刷新{}操作员信息开始========", f_account);

        try {
            if (securityUserCache.containsKey(f_account)) {
                securityUserCache.remove(f_account);

                if (user.getF_is_can_login() == 1) {
                    SecurityUser securityUser = new SecurityUser(user, getUserAuthorities(user));
                    securityUserCache.put(securityUser.getUsername(), securityUser);
                }
            }
        } finally {
            LOG.info("========刷新{}操作员信息完成，耗时：{}ms========", f_account, (System.currentTimeMillis() - startTime));
        }
    }

    private List<GrantedAuthority> getUserAuthorities(UserEntity user) {
        ParameterMap params = new ParameterMap("f_status", 1);
        if (!user.isSuperAdmin()) {
            params.put("f_user_id", user.getF_id());
        }
        List<MenuEntity> menuList = userMenuMapper.selectUserAndRoleMenuListPage(params);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (MenuEntity menu : menuList) {
            authorities.add(getAuthority(menu.getF_id()));
        }

        return authorities;
    }

    private SecurityAuthority getAuthority(String menuId) {
        SecurityAuthority authority = authoritiesCache.get(menuId);
        if (authority == null) {
            authority = new SecurityAuthority(menuId);
            authoritiesCache.put(menuId, authority);
        }
        return authority;
    }

    public Collection<ConfigAttribute> getRequestAuthorities(final HttpServletRequest request) {
        String url = urlPathHelper.getLookupPathForRequest(request);
        UrlEntity urlEntity = RequestMappingInfoUtil.getUrlEntity(request, requestMappingHandlerMapping);
        // 未匹配到后台controller处理方法的，为前台静态资源路径
        if (urlEntity == null) {
            LOG.debug("请求[" + request.getMethod() + "][" + url + "]，配置权限：[]。");
            return null;
        }

        Collection<ConfigAttribute> authorityList = urlAuthoritiesCache.get(urlEntity.getF_id());
        LOG.debug("请求[" + request.getMethod() + "][" + url + "]，配置权限：[" + authorityList + "]。");

        // 后台URL地址如果未配置访问权限
        if (authorityList == null) {
            return unconfigAuthority;
        }

        return authorityList;
    }

    public SecurityUser getSecurityUser(String username) {
        // 如果不采用集群部署，可以使用本地单机版的缓存。
        // return getSecurityUserFromLocalCache(username);
        return getSecurityUserFromDB(username);
    }

    // // 如果不采用集群部署，可以使用本地单机版的缓存。
    // private SecurityUser getSecurityUserFromLocalCache(String username) {
    // SecurityUser securityUser = securityUserCache.get(username);
    // if (securityUser == null) {
    // securityUser = getSecurityUserFromDB(username);
    // securityUserCache.put(securityUser.getUsername(), securityUser);
    // }
    //
    // return securityUser;
    // }

    private SecurityUser getSecurityUserFromDB(String f_account) {
        UserEntity user = userMapper.selectUserByAccount(f_account);
        if (user != null && user.getF_is_can_login() == 1) {
            return new SecurityUser(user, getUserAuthorities(user));
        }

        LOG.error("账号[{}]不存在！", f_account);
        throw new UsernameNotFoundException("账号[" + f_account + "]不存在");
    }
}
