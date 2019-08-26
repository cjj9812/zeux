package com.nanshen.web.enums;

import com.nanshen.common.enums.IEnum;

public enum SysUserEnum implements IEnum {

    EXIST(500001,"用户已存在"),
    NOT_EXIST(500002,"用户不存在"),

    ;

    private int code;

    private String msg;

    SysUserEnum(int code,String msg){
        this.code=code;
        this.msg=msg;
    }



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
