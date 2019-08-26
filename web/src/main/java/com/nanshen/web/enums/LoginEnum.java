package com.nanshen.web.enums;

import com.nanshen.common.enums.IEnum;

public enum LoginEnum implements IEnum {

    LOGIN_SUCCESS(0,"登录成功"),
    LOGOUT_SUCCESS(0,"退出成功"),
    NAME_NOT_EMPTY(500003,"用户名或密码不能为空"),
    PWD_INCORRECT(500004,"密码不正确"),
    USER_LOCK(500005,"密码错误次数超过5次，账户锁定"),
    ;

    LoginEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
    private Integer code;

    private String msg;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public void setCode(int code) {
        this.code=code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg=msg;
    }
}
