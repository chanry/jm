package com.cl.jm.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.cl.jm.service.JmServiceImpl;

@Configuration
public class JmConfiguration {

	@Bean  
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){  
		RestTemplate restTemplate = new RestTemplate(factory);

		List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
		httpMessageConverters.stream().forEach(httpMessageConverter -> {
			if(httpMessageConverter instanceof StringHttpMessageConverter){
				StringHttpMessageConverter messageConverter = (StringHttpMessageConverter) httpMessageConverter;
				messageConverter.setDefaultCharset(Charset.forName("UTF-8"));
			}
		});

        return restTemplate;  
    } 

	
	@Bean  
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){  
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();  
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(15000); 
        return factory;  
    }  
	
	@Bean
	public JmServiceImpl jmServiceImpl() {
		return new JmServiceImpl();
	}
	
}
