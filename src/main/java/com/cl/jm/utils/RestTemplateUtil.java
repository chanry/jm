package com.cl.jm.utils;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 说明：为方便restTemplate的get方式的url参数拼接和post的表单提交，简单封装两个方法
 * 
 * @author chenli
 * @time 2018年1月18日 上午10:03:26
 */
@Component
public class RestTemplateUtil {

	@Autowired
	private RestTemplate restTemplate;

	private static RestTemplateUtil restTemplateUtil;
	
	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * 通过init方法，
	 * 1.注入bean(restTemplate) 
	 * 2.赋值给static RestTemplateUtil restTemplateUtil
	 * 3.使用restTemplate的时候，就通过restTemplateUtil来取
	 */
	@PostConstruct
	public void init() {
		restTemplateUtil = this;
		restTemplateUtil.restTemplate = this.restTemplate;
	}

	public static <T> T getForObject(String url, Class<T> responseType, Map<String, Object> params) {
		url = url.trim() + "?";
		if (null != params) {
			for (String key : params.keySet()) {
				url += key + "=" + "{" + key + "}" + "&";
			}
			url = url.substring(0, url.length() - 1);
		}
		
		return restTemplateUtil.restTemplate.getForObject(url, responseType, params);
	}

	public static <T> ResponseEntity<T> postForObject(String url, Class<T> responseType, Map<String, Object> params) {
		HttpHeaders headers = new HttpHeaders();
		// 请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		// 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
		MultiValueMap<String, Object> newParams = new LinkedMultiValueMap<String, Object>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			newParams.add(entry.getKey(), entry.getValue());
		}
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
				newParams, headers);
		return restTemplateUtil.restTemplate.postForEntity(url, requestEntity, responseType);
	}

}
