package com.nanshen.common.exceptions;

import com.nanshen.common.enums.IEnum;
import lombok.Data;

@Data
public class SelfException extends Exception {
    //报错返回enum
    public IEnum iEnum;


    public SelfException(IEnum iEnum){
        super(iEnum.getMsg());
        this.iEnum=iEnum;
    }

    public SelfException (String message){
        super(message);
    }

}
