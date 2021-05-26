package com.cl.jm.entity;

/**
 * @author chenli
 *
 */
public class JEResult {
	
	private JEInfo list;
	
	private String errmsg;
	
	private int status;



	public JEInfo getList() {
		return list;
	}

	public void setList(JEInfo list) {
		this.list = list;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
