package com.cl.jm.model;

public class WebSite {
	
	private String site;
	
	private String name;
	
	
	public WebSite(String site, String name) {
		super();
		this.site = site;
		this.name = name;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
