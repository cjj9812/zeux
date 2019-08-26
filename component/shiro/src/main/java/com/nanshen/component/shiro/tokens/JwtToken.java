package com.nanshen.component.shiro.tokens;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {

	private String token;
	
	public JwtToken(String token) {
		// TODO Auto-generated constructor stub
		this.token=token;
	}
	
	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return token;
	}

	@Override
	public Object getCredentials(){
		// TODO Auto-generated method stub
		return token;
	}

}
