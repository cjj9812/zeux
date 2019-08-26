package ${packageName};


public class ReMessage<T> {
	private Integer code;
	private String message;
	private T data;
	
	public ReMessage(){
		
	}
	public ReMessage(Integer code,String message){
		this.code=code;
		this.message=message;
	}
	
	public ReMessage(Integer code,String message,T data){
		this.code=code;
		this.message=message;
		this.data=data;
	}
	
	public ReMessage(IEnum IEnum) {
        this.code = IEnum.getCode();
        this.message = IEnum.getMsg();
    }

    public ReMessage(IEnum IEnum, T data) {
        this.code = IEnum.getCode();
        this.message = IEnum.getMsg();
        this.data = data;
    }
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	

}
