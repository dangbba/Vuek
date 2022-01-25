package com.ssafy.backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	// 테스트용 front와 연동 이후 링크 보고  수정[시작 ]
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
    }
	// 테스트용 front와 연동 이후 링크 보고  수정[끝 ]
	
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
