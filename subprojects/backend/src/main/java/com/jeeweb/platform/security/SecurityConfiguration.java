/**
 * 
 */
package com.jeeweb.platform.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.security.filter.RestAccessDecisionManager;
import com.jeeweb.platform.security.filter.RestSecurityFilter;
import com.jeeweb.platform.security.filter.RestSecurityMetadataSource;
import com.jeeweb.platform.security.filter.RestTokenProcessingFilter;
import com.jeeweb.platform.security.handler.RestAccessDeniedHandler;
import com.jeeweb.platform.security.handler.RestAuthenticationEntryPoint;
import com.jeeweb.platform.security.handler.RestLogoutSuccessHandler;
import com.jeeweb.platform.security.service.RestTokenService;
import com.jeeweb.platform.security.service.SecurityUserDetailsService;

/**
 * @author 袁进勇
 *
 */
@Configuration
@Order(110)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    // private static final String PARAMETER_USER_NAME = "f_account";
    // private static final String PARAMETER_PASSWORD = "f_password";
    // private static final String API_LOGIN = "/api/login";
    private static final String API_LOGOUT = "/api/logout";
    private static final String API_TOKEN = "/api/platform/security/token";

    @Autowired
    private RestTokenProcessingFilter restTokenProcessingFilter;

    // @Autowired
    // private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
    // @Autowired
    // private RestAuthenticationFailureHandler restAuthenticationFailureHandler;
    @Autowired
    private RestLogoutSuccessHandler restLogoutSuccessHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    private RestAccessDecisionManager restAccessDecisionManager;
    @Autowired
    private RestSecurityMetadataSource restSecurityMetadataSource;

    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    @Value("${spring.security.permitAllUrls:#{null}}")
    private String permitAllUrls;
    @Value("${spring.security.authenticatedUrls:#{null}}")
    private String authenticatedUrls;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 配置不走Spring Security权限控制的URL地址
        web.ignoring().antMatchers("/web/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用CSRF和HTTPSession，采用自定义的RestToken进行认证
        http.addFilterAfter(restTokenProcessingFilter, SecurityContextPersistenceFilter.class) // 添加RestToken的过滤器
                .cors().configurationSource(corsConfigurationSource()) // 启用CORS跨域，使用corsConfigurationSource这个Bean中的跨域配置。
                .and().csrf().disable() // 禁用CSRF，RestToken来防止跨站攻击
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 禁用HTTPSession，Restful采用无Session的管理

        // 对登录进行配置
        http.httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint);
        // http.formLogin() //
        // 这里使用RESTful，不需要配置登录成功、失败的跳转地址successForwardUrl和failureForwardUrl，改为配置：successHandler和failureHandler
        // .usernameParameter(PARAMETER_USER_NAME).passwordParameter(PARAMETER_PASSWORD) // 配置登录时传过来的用户名、密码字段名称
        // .loginProcessingUrl(API_LOGIN) // 配置登录处理的URL，这个URL对应的API不需要自己写，Spring Security会自动拦截提交到此URL的请求，将其视为登录请求。
        // .successHandler(restAuthenticationSuccessHandler) // 认证通过之后，将调用该对象的onAuthenticationSuccess方法
        // .failureHandler(restAuthenticationFailureHandler); // 认证失败之后，将调用该对象的onAuthenticationFailure方法

        // 对登出进行配置
        http.logout() // 这里使用RESTful，不需要配置登出成功之后的调整地址logoutSuccessUrl，改为配置：logoutSuccessHandler
                .logoutUrl(API_LOGOUT) // 登出处理的URL，这个URL对应的API不需要自己写，Spring Security会自动拦截提交到此URL的请求，对用户执行登出操作。
                .logoutSuccessHandler(restLogoutSuccessHandler); // 登出成功后，将调用该对象的onLogoutSuccess方法

        // 配置对异常的处理
        http.exceptionHandling() // 这里使用RESTful，需要把对应的异常处理改为不跳转，返回HTTP状态码
                .authenticationEntryPoint(restAuthenticationEntryPoint) // 当用户请求的操作需要登录时，将抛出AuthenticationException异常，并且将该异常传入到该对象的commence方法。
                .accessDeniedHandler(restAccessDeniedHandler); // 当用户没有访问URL地址的权限时，将调用该对象的handle方法

        // 设置URL的访问权限（授权）
        // 这里不使用Spring Security自带的URL权限匹配，改为通过数据库配置权限的授权规则
        http.addFilterBefore(restSecurityFilter(), FilterSecurityInterceptor.class); // 添加自定义的过滤器，读取数据库中的权限配置来判断是否可以访问URL地址
        http.authorizeRequests().anyRequest().permitAll(); // 所有URL都放通，在上面的restSecurityFilter中进行权限控制
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin(CorsConfiguration.ALL);
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.addExposedHeader(RestTokenService.REST_TOKEN);
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.DELETE);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }

    @Bean
    public RestSecurityFilter restSecurityFilter() throws Exception {
        RestSecurityFilter filter = new RestSecurityFilter();
        // 配置不登陆（授权）也可以访问的URL
        filter.addPermitAllMatchers(API_TOKEN);
        if (!HelpUtil.isEmpty(permitAllUrls)) {
            filter.addPermitAllMatchers(permitAllUrls.trim().split(","));
        }

        // 配置只要登陆即可访问的URL
        filter.addAuthenticatedMatchers(API_LOGOUT);
        if (!HelpUtil.isEmpty(authenticatedUrls)) {
            filter.addAuthenticatedMatchers(authenticatedUrls.trim().split(","));
        }

        filter.setAuthenticationEntryPoint(restAuthenticationEntryPoint);
        filter.setSecurityMetadataSource(restSecurityMetadataSource);
        filter.setAccessDecisionManager(restAccessDecisionManager);
        filter.setAuthenticationManager(authenticationManager());
        filter.afterPropertiesSet();
        return filter;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(securityUserDetailsService) // 当用户执行登录时，将调用UserDetailsService对象的loadUserByUsername方法，将username传入此方法，根据username获取一个UserDetails对象。
                .passwordEncoder(passwordEncoder()); // 在数据库中密码保存为不可逆加密串，在验证密码是否正确时，需要对用户输入的明文密码进行bcrypt加密后比较密文是否一致，故这里需要提供一个BCryptPasswordEncoder对象。
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
