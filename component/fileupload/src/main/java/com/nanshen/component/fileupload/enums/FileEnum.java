package com.nanshen.component.fileupload.enums;

import com.nanshen.common.enums.IEnum;

public enum FileEnum implements IEnum {
    NOT_UOLOAD(500051,"未上传文件"),
    NOT_SUPPORT_TYPE(500052,"不支持文件类型"),
    NOT_SUPPORT_SIZE(500053,"不支持文件大小")
    ;

    private int code;

    private String msg;

    FileEnum(int code,String msg){
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
