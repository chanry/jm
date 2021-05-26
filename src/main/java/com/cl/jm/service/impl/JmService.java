package com.cl.jm.service.impl;

import java.util.List;

import com.cl.jm.entity.JMInfo;
import com.cl.jm.exception.JmException;

public interface JmService {
	
	public List<JMInfo> findLLJmInfoList(String[] idArr) throws JmException;
	
	public List<JMInfo> findJEJmInfoList(String[] idArr) throws JmException;
	
	public List<JMInfo> findOMJmInfoList(String[] idArr) throws JmException;
	
	public List<JMInfo> findZMJmInfoList(String[] idArr) throws JmException;

}
