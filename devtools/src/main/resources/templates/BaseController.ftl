package com.nanshen.module.system;


public class BaseController<T> {

    protected ReMessage success(){
    ReMessage reMessage=new ReMessage(SystemEnum.SUCCESS);
    return reMessage;
    }

    protected ReMessage success(T data){
    ReMessage reMessage=new ReMessage(SystemEnum.SUCCESS,data);
    return reMessage;
    }

    protected ReMessage success(IEnum baseEnum){
    ReMessage reMessage=new ReMessage(SystemEnum.SUCCESS,baseEnum.getMsg());
    return reMessage;
    }

    protected ReMessage error(){
    ReMessage reMessage=new ReMessage(SystemEnum.ERROR);
    return reMessage;
    }

    protected ReMessage error(IEnum baseEnum){
    ReMessage reMessage=new ReMessage(baseEnum);
    return reMessage;
    }

    protected ReMessage error(IEnum baseEnum,T data){
    ReMessage reMessage=new ReMessage(baseEnum,data);
    return reMessage;
    }
    }
