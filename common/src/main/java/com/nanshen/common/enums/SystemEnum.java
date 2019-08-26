package com.nanshen.common.enums;

public enum SystemEnum implements IEnum{
	SUCCESS(0,"操作成功"),
	ERROR(-1,"系统繁忙"),
	UNAUTH(401,"未认证"),
	FORBIDDEN(403,"禁止访问"),
	PARAM_INCORRECT(500000,"参数不正确"),
	NO_DATA(500999,"暂无数据"),


	//公众号
	CODE_INVALIDE(40029,"code无效"),
	TOKEN_INVALIDE(40001,"无效登录凭证"),
	MEDIAID_INVALIDE(40007,"无效的mediaId")

	;

	private Integer code;
	
	private String msg;
	
	private SystemEnum(Integer code,String Msg) {
		// TODO Auto-generated constructor stub
		this.code=code;
		this.msg=Msg;
	}
	
	
	@Override
	public int getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}

	@Override
	public void setCode(int code) {
		// TODO Auto-generated method stub
		this.code=code;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.msg;
	}

	@Override
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
		this.msg=msg;
	}

}
