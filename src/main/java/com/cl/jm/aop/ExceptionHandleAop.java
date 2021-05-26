package com.cl.jm.aop;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cl.jm.entity.JsonResult;
import com.cl.jm.exception.JmException;


/**
 * controller异常捕获处理aop
 * 
 * ExceptionHandleAop.java
 * 
 * @author chenli
 * @time 2018年5月19日 下午5:02:28
 */
@Aspect
@Configuration
public class ExceptionHandleAop {

	private static Logger LOG = Logger.getLogger(ExceptionHandleAop.class);


	// 匹配com.yanda.controller包及其子包下的所有类的所有方法
	@Pointcut("execution(* com.cl..*.controller..*.*(..))")
	public void executeService() {

	}

	/**
	 * 环绕拦截控制层的方法，若发生异常记录异常信息到数据库中
	 * 
	 * @param point
	 * @return
	 */
	@Around("executeService()")
	public Object handleControllerMethod(ProceedingJoinPoint point) {

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		
		
		try {
			return point.proceed();
		} catch (JmException e) {
			LOG.info(e);
			return new JsonResult(e.getStatus(), e.getMessage());
		} catch (Throwable e) {
			LOG.info(e);
			return new JsonResult(500, "服务器内部错误！");
		}
	}
	
	
}
