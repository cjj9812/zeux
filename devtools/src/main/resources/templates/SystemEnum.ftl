package ${packageName};

public enum SystemEnum implements IEnum{
	SUCCESS(0,"操作成功"),
	ERROR(-1,"系统繁忙")
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
