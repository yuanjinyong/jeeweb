/**
 * 
 */
package com.jeeweb.platform.security.web.filter;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import com.jeeweb.platform.security.model.SecurityAuthority;

/**
 * AccessdecisionManager在Spring security中是很重要的。
 * 
 * 在验证部分简略提过了，所有的Authentication实现需要保存在一个GrantedAuthority对象数组中。
 * 这就是赋予给主体的权限。 GrantedAuthority对象通过AuthenticationManager
 * 保存到 Authentication对象里，然后从AccessDecisionManager读出来，进行授权判断。
 * 
 * Spring Security提供了一些拦截器，来控制对安全对象的访问权限，例如方法调用或web请求。
 * 一个是否允许执行调用的预调用决定，是由AccessDecisionManager实现的。
 * 这个 AccessDecisionManager 被AbstractSecurityInterceptor调用，
 * 它用来作最终访问控制的决定。 这个AccessDecisionManager接口包含三个方法：
 * 
 * void decide(Authentication authentication, Object secureObject,
 * List<ConfigAttributeDefinition> config) throws AccessDeniedException;
 * boolean supports(ConfigAttribute attribute);
 * boolean supports(Class clazz);
 * 
 * 从第一个方法可以看出来，AccessDecisionManager使用方法参数传递所有信息，这好像在认证评估时进行决定。
 * 特别是，在真实的安全方法期望调用的时候，传递安全Object启用那些参数。
 * 比如，让我们假设安全对象是一个MethodInvocation。
 * 很容易为任何Customer参数查询MethodInvocation，
 * 然后在AccessDecisionManager里实现一些有序的安全逻辑，来确认主体是否允许在那个客户上操作。
 * 如果访问被拒绝，实现将抛出一个AccessDeniedException异常。
 * 
 * 这个 supports(ConfigAttribute) 方法在启动的时候被
 * AbstractSecurityInterceptor调用，来决定AccessDecisionManager
 * 是否可以执行传递ConfigAttribute。
 * supports(Class)方法被安全拦截器实现调用，
 * 包含安全拦截器将显示的AccessDecisionManager支持安全对象的类型。
 * 
 * 
 * @author 袁进勇
 *
 */
@Component
public class RestAccessDecisionManager implements AccessDecisionManager {
    private static final Logger LOG = LoggerFactory.getLogger(RestAccessDecisionManager.class);

    /*
     * (non-Javadoc)
     * @see org.springframework.security.access.AccessDecisionManager#decide(org.springframework.security.core.
     * Authentication, java.lang.Object, java.util.Collection)
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        FilterInvocation fi = (FilterInvocation) object;
        HttpServletRequest request = fi.getRequest();
        String uri = new UrlPathHelper().getLookupPathForRequest(request);
        String method = request.getMethod();
        LOG.debug("请求[{}][{}]，配置权限：[{}]，已有权限：[{}]。", method, uri, configAttributes, authentication.getAuthorities());

        if (configAttributes == null) {
            return;
        }

        if (configAttributes.size() == 1) {
            ConfigAttribute authority = configAttributes.iterator().next();
            if (SecurityAuthority.UNCONFIGURED_AUTHORITY.equals(authority.getAttribute())) {
                // 一般报未配置的异常时，需要开发人员在开发阶段就把功能（菜单页面和按钮）可以访问的URL地址配置好。
                LOG.error("未配置[" + method + "][" + uri + "]的访问权限，请联系系统技术支持人员！");
                throw new AccessDeniedException("未配置[" + method + "][" + uri + "]的访问权限，请联系系统技术支持人员！");
            }
        }

        for (ConfigAttribute ca : configAttributes) {
            String needRole = ca.getAttribute();
            // 如果为只要登陆即可的权限
            if ("AUTHENTICATED".equals(needRole)) {
                return;
            }

            // ga为用户所被赋予的权限。 needRole为访问相应的资源应该具有的权限。
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needRole.equals(ga.getAuthority())) {
                    return;
                }
            }
        }

        LOG.info("没有[" + method + "][" + uri + "]的访问权限，请联系系统管理员配置权限！");
        throw new AccessDeniedException("没有[" + method + "][" + uri + "]的访问权限，请联系系统管理员配置权限！");
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.access.AccessDecisionManager#supports(org.springframework.security.access.
     * ConfigAttribute)
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.access.AccessDecisionManager#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

}
