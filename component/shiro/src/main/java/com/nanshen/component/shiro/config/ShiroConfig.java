package com.nanshen.component.shiro.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import com.nanshen.component.shiro.auth.MAuthenticator;
import com.nanshen.component.shiro.auth.MCredentialsMatcher;
import com.nanshen.component.shiro.filter.JwtFilter;
import com.nanshen.component.shiro.realms.JwtTokenRealm;
import com.nanshen.component.shiro.realms.LoginRealm;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@EnableCaching
public class ShiroConfig {

    // 登录地址
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    // 权限认证失败地址
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;

    // 首页地址
    @Value("${shiro.user.indexUrl}")
    private String indexUrl;

    // 权限认证失败地址
    @Value("${shiro.cache.maxAge}")
    private Integer maxAge;

    private final Logger logger=LoggerFactory.getLogger(this.getClass());

    //配置过滤器工厂
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {

        logger.info("配置过滤器工厂...");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //配置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //如果没有认证跳转到loginUrl对应页面
//		shiroFilterFactoryBean.setLoginUrl(loginUrl);
        //登录成功后跳转页面，如果程序中有设置，依程序为主
//		shiroFilterFactoryBean.setSuccessUrl(indexUrl);
        //没有权限访问资源跳转页面
//		shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        //设置过滤器链 可自定义也可按照默认来 此处使用自定义过滤器
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //配置需要过滤路由
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/api/v1/login", "anon");  // 访问401不通过我们的Filter
        filterChainDefinitionMap.put("/api/v1/logout", "anon");  // 访问401不通过我们的Filter
        filterChainDefinitionMap.put("/api/v1/pri/activity/export/**", "anon");  // 访问401不通过我们的Filter


        filterChainDefinitionMap.put("/api/v1/pub/**", "anon");  // 访问401不通过我们的Filter
        filterChainDefinitionMap.put("/MP_verify_LO9J3oC9toQeQQWg.txt", "anon");  // 访问401不通过我们的Filter
        filterChainDefinitionMap.put("/wx/*","anon");
        filterChainDefinitionMap.put("/static/**", "anon");  // 访问401不通过我们的Filter
        filterChainDefinitionMap.put("/admin/**", "anon");  // 访问401不通过我们的Filter
        filterChainDefinitionMap.put("/**", "jwt");    // 所有请求通过我们自己的JWT Filter
        filterChainDefinitionMap.put("/401", "anon");  // 访问401不通过我们的Filter
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager(EhCacheManager cacheManager,LoginRealm loginRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //配置认证器
        securityManager.setAuthenticator(modularRealmAuthenticator());
        //设置多个realm
        List<Realm> realms = new ArrayList<>();
        realms.add(tokenRealm());
        realms.add(loginRealm);
        securityManager.setRealms(realms);
        //禁用sessionStorage
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setCacheManager(cacheManager);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    @Bean
    public LoginRealm loginRealm(HashedCredentialsMatcher hashedCredentialsMatcher){
        LoginRealm loginRealm=new LoginRealm();
        //给loginRealm设置自己写的凭证匹配器
        loginRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return loginRealm;
    }

    @Bean
    public JwtTokenRealm tokenRealm(){
        JwtTokenRealm tokenRealm=new JwtTokenRealm();
        return tokenRealm;

    }

    /**
     * shiro缓存管理器;
     * @return
     */
    @Bean("customCacheManager")
    @Primary
    public EhCacheManager ehCacheManager(CacheManager cacheManager){
        EhCacheManager ehCacheManager = new EhCacheManager();
//        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        ehCacheManager.setCacheManager(cacheManager);       //使用net.sf.ehcache的cacheManager
        return ehCacheManager;
    }

    /**
     * 自定义凭证匹配器
     * 凭证匹配器 密码校验交给Shiro的SimpleAuthenticationInfo进行处理
     * 添加密码错误重试次数限制
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(EhCacheManager ehCacheManager){
        HashedCredentialsMatcher hashedCredentialsMatcher = new MCredentialsMatcher(ehCacheManager);
//		hashedCredentialsMatcher.setHashAlgorithmName("MD5");	//加密算法名
//		hashedCredentialsMatcher.setHashIterations(1024);	//加密次数
        return hashedCredentialsMatcher;

    }

    /**
     * 自定义的认证器
     * 有一个realm通过则认证通过
     * */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        ModularRealmAuthenticator modularRealmAuthenticator=new MAuthenticator();
        //设置认证策略，这里设定只要一个realm通过即身份通过认证，返回所有realm
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }





    /**
     * @描述：开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
