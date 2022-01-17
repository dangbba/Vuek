package com.ssafy.backend.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/*registry.addInterceptor(interceptor)
				.addPathPatterns("/user/**")
				.addPathPatterns("/notice/**")
				.addPathPatterns("/aptdeal/**")
				.addPathPatterns("/news/**")
				.excludePathPatterns("/user/login")
				.excludePathPatterns("/user/register"); */
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}
