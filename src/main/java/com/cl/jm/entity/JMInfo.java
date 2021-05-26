package com.cl.jm.entity;

public class JMInfo {
	
	private String id;
	
	private String money;
	
	private String name;
	
	private String url;
	
	

	public JMInfo(String id) {
		super();
		this.id = id;
	}



	public JMInfo(String id, String money) {
		super();
		this.id = id;
		this.money = money;
	}
	
	

	public JMInfo(String id, String money, String name) {
		super();
		this.id = id;
		this.money = money;
		this.name = name;
	}

	

	public JMInfo(String id, String money, String name, String url) {
		super();
		this.id = id;
		this.money = money;
		this.name = name;
		this.url = url;
	}




	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
