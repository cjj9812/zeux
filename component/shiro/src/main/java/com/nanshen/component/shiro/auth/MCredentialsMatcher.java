package com.nanshen.component.shiro.auth;


import com.nanshen.common.utils.MD5Util;
import com.nanshen.component.shiro.tokens.MUsernamePasswordToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.concurrent.atomic.AtomicInteger;


public class MCredentialsMatcher extends HashedCredentialsMatcher {

    private Cache<String, AtomicInteger> passwordRetryCache;
    private Logger logger  = LoggerFactory.getLogger(this.getClass());

    public MCredentialsMatcher(CacheManager cacheManager) {
        //读取ehcache中配置的登录限制锁定时间
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    /**
     *
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,
                                      AuthenticationInfo info) {
    	MUsernamePasswordToken upToken = (MUsernamePasswordToken) token;
        //获取登录用户名
        String username = (String) upToken.getPrincipal();
        //从ehcache中获取密码输错次数
        // retryCount
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            //第一次
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        //retryCount.incrementAndGet()自增：count + 1
        if (retryCount.incrementAndGet() > 5) {
            // if retry count > 5 throw  超过5次 锁定
            throw new ExcessiveAttemptsException("username:" + username + " tried to login more than 5 times in period");
        }

        //否则走判断密码逻辑
        if (upToken.getCredentials() == null) {
            throw new NullPointerException("密码为null");
        }
        String secret = (String) upToken.getCredentials();

        SimpleAuthenticationInfo info1=(SimpleAuthenticationInfo) info;
        ByteSource byteSource=info1.getCredentialsSalt();
//        String credentials1=new Md5Hash(secret, byteSource, 1024).toString();
        String credentials1= MD5Util.strMd5(secret);
        String credentials2 = (String) info.getCredentials();
        if (!credentials1.equals(credentials2)) {
            throw new IncorrectCredentialsException("密码不正确");
        }
        // clear retry count  清除ehcache中的count次数缓存
        passwordRetryCache.remove(username);
        return true;
    }
}
