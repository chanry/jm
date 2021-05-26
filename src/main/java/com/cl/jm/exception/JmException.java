package com.cl.jm.exception;


public class JmException extends Exception {

	/**
	 * @Field @serialVersionUID : TODO(这里用一句话描述这个类的作用)
	 */
	private static final long serialVersionUID = 2218600185497325486L;
	
	/**
	 * 异常消息
	 */
	private String message = "请求失败";
	
	/**
	 * 请求状态码
	 */
	private int status = -1;
	
	
	public JmException(String message) {
		super(message);
		this.message = message;
	}
	
	public JmException(String message, Exception exception) {
		super(message, exception);
		this.message = message;
	}


	/**
	 * @return the 异常消息
	 */
	public String getMessage() {
		return message;
	}
	

	/**
	 * @param message the 异常消息 to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
