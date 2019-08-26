package com.nanshen.component.shiro.tokens;

import org.apache.shiro.authc.AuthenticationToken;

public class MUsernamePasswordToken implements  AuthenticationToken {

    //用户名
    private String name;

    //密码
    private String password;

    public MUsernamePasswordToken(String name, String password){
        this.name=name;
        this.password=password;
    }

    @Override
    public Object getPrincipal() {
        return name;
    }

    @Override
    public Object getCredentials() {
        return password;
    }
}
