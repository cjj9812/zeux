package com.nanshen.component.shiro.realms;

import com.nanshen.component.jwt.utils.JwtUtil;
import com.nanshen.component.shiro.tokens.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;



public class JwtTokenRealm extends AuthorizingRealm {

	//仅支持JwtToken
	@Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
	
	
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		String principals1=(String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo sai=new SimpleAuthorizationInfo();
		sai.addRole("user");
		sai.addStringPermission("user:view");
		return sai ;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
		// TODO Auto-generated method stub
		String jwtToken=(String)auth.getPrincipal();
		if(jwtToken==null){
			throw new AuthenticationException("token is null");
		}
		String id= JwtUtil.getId(jwtToken);
		if(id==null){
			throw new AuthenticationException("token invalid");
		}
		boolean b=JwtUtil.verify(jwtToken, id);
		if (!JwtUtil.verify(jwtToken, id)) {
			throw new AuthenticationException("Username or password error");
		}

 		return new SimpleAuthenticationInfo(jwtToken, jwtToken, getName());
		
		
	}

}
