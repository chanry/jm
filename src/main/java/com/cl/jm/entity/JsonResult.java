package com.cl.jm.entity;

public class JsonResult {
	
	/**
	 * 响应状态码
	 */
	private int status;
	
	/**
	 * 响应消息
	 */
	private String message;
	
	/**
	 * 返回的json数据
	 */
	private Object data;
	
    
	public JsonResult() {
		
	}
	
	public JsonResult(int status, String message) {
		super();
		this.status = status;
		this.message = message;
		this.data = null;
	}
	
	public JsonResult(int status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public void setData(Object data) {
		this.data = data;
	}
	
}
