package com.costrategix.gbp.capstone2.actuator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class ApiConfig {
	

	@Bean 
	public FreeMarkerConfigurationFactoryBean factoryBean() {
		FreeMarkerConfigurationFactoryBean bean=new FreeMarkerConfigurationFactoryBean();
//		bean.setTemplateLoaderPath("C:\\Users\\Inno\\Desktop\\cap09\\capstone-2-awsspp-be\\capstone2\\src\\main\\resources\\templates\\email-template.ftl");
		bean.setTemplateLoaderPath("file:///${spring.config.location}/cap09/capstone-2-awsspp-be/capstone2/src/main/resources/templates/email.ftl");
		
		return bean;
	}

}