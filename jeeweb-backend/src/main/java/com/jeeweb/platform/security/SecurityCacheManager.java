/**
 * 
 */
package com.jeeweb.platform.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.platform.sys.entity.UrlEntity;
import com.jeeweb.platform.sys.mapper.UrlMapper;

/**
 * @author 袁进勇
 *
 */
@Component
public class SecurityCacheManager implements InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityCacheManager.class);

    // private static List<MenuEntity> pagesCache = new ArrayList<>();
    // // 权限缓存
    // private static Map<String, CustomAuthority> authoritiesCache = new HashMap<String, CustomAuthority>();
    // // URL配置的权限缓存
    // private static Map<String, Collection<ConfigAttribute>> urlAuthoritiesCache = new HashMap<String,
    // Collection<ConfigAttribute>>();
    // // 后台URL地址如果未配置访问权限
    // private static Collection<ConfigAttribute> unkownAuthority = new ArrayList<ConfigAttribute>();
    // // 操作员缓存
    // private static Map<String, SecurityUser> securityUserCache = new HashMap<String, SecurityUser>();

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired
    private UrlMapper urlMapper;
    // @Autowired
    // private MenuMapper menuMapper;
    // @Autowired
    // private MenuUrlMapper menuUrlMapper;
    // @Autowired
    // private UserMapper userMapper;
    // @Autowired
    // private UserMenuMapper userMenuMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        // unkownAuthority.add(new CustomAuthority("UNKOWN_AUTHORITY"));

        // 在Bean初始化完后，自动扫描URL地址，并加载权限列表。
        updateUrls();
        loadUrlAuthoritiesCache();
        // loadSecurityUserCache();
    }

    private void updateUrls() {
        LOG.info("========更新t_sys_url开始========");

        long startTime = System.currentTimeMillis();
        List<UrlEntity> entityList = new ArrayList<>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        for (Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            entityList.add(buildUrlEntity(entry.getKey(), entry.getValue()));
        }

        urlMapper.deleteEntities(new ParameterMap("f_auto", true));
        urlMapper.insertEntities(entityList);

        LOG.info("========更新t_sys_url完成，耗时：{}ms========", (System.currentTimeMillis() - startTime));
    }

    private UrlEntity buildUrlEntity(RequestMappingInfo mapping, HandlerMethod method) {
        UrlEntity entity = new UrlEntity();
        entity.setF_url(mapping.getPatternsCondition().getPatterns().iterator().next().substring(1));
        entity.setF_desc("");
        entity.setF_patterns(mapping.getPatternsCondition().toString());
        entity.setF_methods(mapping.getMethodsCondition().toString());
        entity.setF_params(mapping.getParamsCondition().toString());
        entity.setF_headers(mapping.getHeadersCondition().toString());
        entity.setF_consumes(mapping.getConsumesCondition().toString());
        entity.setF_produces(mapping.getProducesCondition().toString());
        entity.setF_custom(mapping.getCustomCondition() == null ? "[]" : mapping.getCustomCondition().toString());
        entity.setF_handler_method(method.getMethod().toString());
        entity.setF_is_log(2);
        entity.setF_is_auto(1);

        entity.setF_id(entity.generateF_id());

        return entity;
    }

    public void loadUrlAuthoritiesCache() {
        // loadPagesCache();
        //
        // MapEntity params = new MapEntity();
        // params.put("f_is_show", 1);
        // params.setOrderBy("f_patterns, f_methods");
        // List<MapEntity> menuUrlMapList = menuUrlMapper.selectMapEntityListPage(params);
        //
        // urlAuthoritiesCache.clear();
        //
        // // 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
        // for (MapEntity menuUrlMap : menuUrlMapList) {
        // String urlId = menuUrlMap.getString("f_url_id");
        // Collection<ConfigAttribute> authorityList = urlAuthoritiesCache.get(urlId);
        // if (authorityList == null) {
        // authorityList = new ArrayList<ConfigAttribute>();
        // urlAuthoritiesCache.put(urlId, authorityList);
        // }
        //
        // String menuId = menuUrlMap.getString("f_menu_id");
        // CustomAuthority authority = authoritiesCache.get(menuId);
        // if (authority == null) {
        // authority = new CustomAuthority(menuId);
        // authoritiesCache.put(menuId, authority);
        // }
        // authorityList.add(authority);
        // }
        //
        // // 刷新超级管理员的权限
        // SecurityUser superAdmin = securityUserCache.get(UserEntity.SUPERADMIN);
        // if (superAdmin != null) {
        // UserEntity user = superAdmin.getUser();
        // user.setF_password(superAdmin.getPassword());
        // loadSecurityUserCache(user);
        // }
    }

    // private void loadPagesCache() {
    // MapEntity params = new MapEntity();
    // params.put("f_type", 2);
    // params.put("f_is_show", 1);
    // params.setPageSizeWithMax().setOrderBy("f_parent_ids, f_order");
    // List<MenuEntity> pages = menuMapper.selectEntityListPage(params);
    // pagesCache.clear();
    // pagesCache.addAll(pages);
    // }
    //
    // public void loadSecurityUserCache() {
    // long startTime = System.currentTimeMillis();
    // LOG.info("========加载操作员信息开始========");
    //
    // securityUserCache.clear();
    //
    // MapEntity params = new MapEntity();
    // params.put("f_is_can_login", 1);
    // List<UserEntity> userList = userMapper.selectEntityListPage(params);
    // for (UserEntity user : userList) {
    // SecurityUser securityUser = new SecurityUser(user.getF_account(), user.getF_password(),
    // getAuthorities(user));
    // securityUser.setUser(user);
    // user.setF_password(null);
    // securityUserCache.put(securityUser.getUsername(), securityUser);
    // }
    //
    // LOG.info("========加载操作员信息完成，耗时：{}ms========", (System.currentTimeMillis() - startTime));
    // }
    //
    // public void loadSecurityUserCache(UserEntity user) {
    // long startTime = System.currentTimeMillis();
    // LOG.info("========刷新{}操作员信息开始========", user.getF_account());
    //
    // try {
    // SecurityUser securityUser = securityUserCache.get(user.getF_account());
    //
    // if (securityUser != null) {
    // securityUserCache.remove(securityUser);
    // }
    // if (user.getF_is_can_login() != 1) {
    // return;
    // }
    //
    // securityUser = new SecurityUser(user.getF_account(), user.getF_password(), getAuthorities(user));
    // securityUser.setUser(user);
    // user.setF_password(null);
    // securityUserCache.put(securityUser.getUsername(), securityUser);
    // } finally {
    // LOG.info("========刷新{}操作员信息完成，耗时：{}ms========", user.getF_account(),
    // (System.currentTimeMillis() - startTime));
    // }
    // }
    //
    // private List<GrantedAuthority> getAuthorities(UserEntity user) {
    // MapEntity params = new MapEntity();
    // if (!user.isSuperAdmin()) {
    // params.put("f_user_id", user.getF_id());
    // }
    // params.put("f_is_show", 1);
    // List<MenuEntity> menuList = userMenuMapper.selectUserAndRoleMenuListPage(params);
    //
    // List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    // for (MenuEntity menu : menuList) {
    // CustomAuthority authority = authoritiesCache.get(menu.getF_id());
    // if (authority == null) {
    // authority = new CustomAuthority(menu.getF_id());
    // authoritiesCache.put(menu.getF_id(), authority);
    // }
    //
    // authorities.add(authority);
    // }
    //
    // return authorities;
    // }
    //
    // public Collection<ConfigAttribute> getRequestAuthorities(final HttpServletRequest request) {
    // String url = new UrlPathHelper().getLookupPathForRequest(request);
    // RequestMappingInfo bestMatchInfo = getRequestMappingInfo(request);
    // // 未匹配到后台controller处理方法的，为前台静态资源路径
    // if (bestMatchInfo == null) {
    // LOG.debug("Request [" + url + "][" + request.getMethod() + "], Authorities: []");
    // return null;
    // }
    //
    // String urlId = generateUrlId(bestMatchInfo);
    // Collection<ConfigAttribute> authorityList = urlAuthoritiesCache.get(urlId);
    // LOG.debug("Request [" + url + "][" + request.getMethod() + "], Authorities: " + authorityList);
    //
    // // 后台URL地址如果未配置访问权限
    // if (authorityList == null) {
    // return unkownAuthority;
    // }
    //
    // return authorityList;
    // }
    //
    // private RequestMappingInfo getRequestMappingInfo(final HttpServletRequest request) {
    // List<RequestMappingInfo> matches = new ArrayList<>();
    // Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
    // for (Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
    // RequestMappingInfo info = entry.getKey().getMatchingCondition(request);
    // if (info != null) {
    // matches.add(info);
    // }
    // }
    //
    // if (matches.size() == 0) {
    // return null;
    // }
    //
    // if (matches.size() > 1) {
    // Collections.sort(matches, new Comparator<RequestMappingInfo>() {
    // @Override
    // public int compare(RequestMappingInfo info1, RequestMappingInfo info2) {
    // return info1.compareTo(info2, request);
    // }
    // });
    // }
    //
    // return matches.get(0);
    // }

    // public SecurityUser getSecurityUser(String username) {
    // return securityUserCache.get(username);
    // }
    //
    // public List<MenuEntity> getAllPages() {
    // return pagesCache;
    // }
}
