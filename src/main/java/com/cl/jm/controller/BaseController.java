package com.cl.jm.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.cl.jm.entity.JsonResult;
import com.cl.jm.utils.ActionResultUtil;
import com.cl.jm.utils.StringUtil;


/**
 * 控制基础类
 * @author chenli
 *
 */
public class BaseController {
	/**
	 * 日志记录，继承BaseController的控制类可直接调用
	 */
	protected Logger LOG = Logger.getLogger(this.getClass());
	

	/**
	 * 接口数据返回
	 * @param status  请求响应码   -1-失败 200-成功
	 * @param message 响应消息结果
	 * @return
	 */
	public JsonResult result(int status, String message) {
		return this.result(status, message, null);
	}
	
	/**
	 * 接口数据返回
	 * @param status  请求响应码   -1-失败 200-成功
	 * @param message 响应消息结果
	 * @data data 响应数据
	 * @return
	 */
	public JsonResult result(int status, String message, Object data) {
		return ActionResultUtil.result(status, message, data);
	}
	
	/**
	 * 从请求体中获取指定参数值
	 * @param request 请求体
	 * @param name 参数名
	 * @param defaultVal 参数值为空时的默认值
	 * @return
	 */
	public String getValue(HttpServletRequest request, String name, String defaultVal) {
		String value = request.getParameter(name);
		if (StringUtil.isEmpty(value))
			return defaultVal;
		return value;
	}
	
	public Integer getIntValue(HttpServletRequest request, String name, Integer defaultVal) {
		String value = request.getParameter(name);
		if (StringUtil.isEmpty(value))
			return defaultVal;
		return Integer.valueOf(value);
	}
	
	public Integer getIntValue(HttpServletRequest request, String name) {
		return getIntValue(request, name, null);
	}
	
	/**
	 * 从请求体中获取指定参数值，若为null则返回空字符串
	 * @param request 请求体
	 * @param name 参数名
	 * @return
	 */
	public String getNotEmptyValue(HttpServletRequest request, String name) {
		return getValue(request, name, "");
	}
	
	
}
