package com.ssafy.backend.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!registry.hasMappingForPattern("/swagger-ui.html")) {
			registry.addResourceHandler("/**/*").addResourceLocations("classpath:/static/").resourceChain(true)
			.addResolver(new PathResourceResolver() {
				@Override
				protected Resource getResource(String resourcePath, Resource location) throws IOException {
					Resource requestedResource = location.createRelative(resourcePath);
					return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
							: new ClassPathResource("/static/index.html");
				}
			});
		}
	}
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/**/*").addResourceLocations("classpath:/static/").resourceChain(true)
//				.addResolver(new PathResourceResolver() {
//					@Override
//					protected Resource getResource(String resourcePath, Resource location) throws IOException {
//						Resource requestedResource = location.createRelative(resourcePath);
//						return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
//								: new ClassPathResource("/static/index.html");
//					}
//				});
//	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/*
		 * registry.addInterceptor(interceptor) .addPathPatterns("/user/**")
		 * .addPathPatterns("/notice/**") .addPathPatterns("/aptdeal/**")
		 * .addPathPatterns("/news/**") .excludePathPatterns("/user/login")
		 * .excludePathPatterns("/user/register");
		 */
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
