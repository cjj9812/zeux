package com.nanshen.component.shiro.realms;


import com.nanshen.component.jwt.utils.JwtUtil;
import com.nanshen.component.shiro.tokens.MUsernamePasswordToken;
import com.nanshen.module.system.entity.SysUser;
import com.nanshen.module.system.service.SysUserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

//import com.nanshen.shirotoken.util.Md5Util;


public class LoginRealm extends AuthorizingRealm {
	
	@Autowired
	private SysUserService userService;
	
	//
	@Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof MUsernamePasswordToken;
    }
	

	//模拟数据库获取user
	
	
	public static String Md(String credentials){
		//加密密码，在注册时对密码进行加密
		String secret=new SimpleHash("MD5", credentials, ByteSource.Util.bytes("456"), 1024).toString();
		System.out.println(secret);
		return secret;
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws UnknownAccountException{
		// TODO Auto-generated method stub
		MUsernamePasswordToken usernamePasswordToken=(MUsernamePasswordToken)token;
		String name=(String)usernamePasswordToken.getPrincipal();
		SysUser user=userService.findByName(name);
		//这一步必须有，shiro不提供用户名校验
		if(user==null){
			throw new UnknownAccountException("用户不存在");
		}
		//将保存在数据库的用户名，密码，盐等封装成一个认证info ,底层比对token中的密码跟info的密码，从而校验密码是否正确
		return new SimpleAuthenticationInfo(user.getName(), user.getPassword(),getName());
		
	}

	
}
