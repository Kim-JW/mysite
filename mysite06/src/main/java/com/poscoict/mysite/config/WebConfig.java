package com.poscoict.mysite.config;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.poscoict.mysite.security.AuthInterceptor;
import com.poscoict.mysite.security.AuthUserHandlerMethodArgumentResolver;
import com.poscoict.mysite.security.LoginInterceptor;
import com.poscoict.mysite.security.LogoutInterceptor;
import com.poscoict.mysite.security.SiteInterceptor;

@SpringBootConfiguration
@PropertySource("classpath:com/poscoict/mysite/config/WebConfig.properties")
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private Environment env;
	
	// Argument Resolver
		@Bean
		public HandlerMethodArgumentResolver handlerMethodArgumentResolver() {
			return new AuthUserHandlerMethodArgumentResolver();
		}

		@Override
		public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
			argumentResolvers.add(handlerMethodArgumentResolver());
		}
		
		// Interceptors
		@Bean
		public HandlerInterceptor loginInterceptor() {
			return new LoginInterceptor();
		}
		
		@Bean
		public HandlerInterceptor logoutInterceptor() {
			return new LogoutInterceptor();
		}
		
		@Bean
		public HandlerInterceptor authInterceptor() {
			return new AuthInterceptor();
		}
		
		@Bean
		public HandlerInterceptor siteInterceptor() {
			return new SiteInterceptor();
		}
		
		

		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(loginInterceptor()).addPathPatterns("/user/auth");
			
			registry.addInterceptor(logoutInterceptor()).addPathPatterns("/user/logout");
			
			registry
			.addInterceptor(authInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/user/auth")
			.excludePathPatterns("/user/logout")
			.excludePathPatterns("/assets/**");
			
			registry
			.addInterceptor(siteInterceptor())
			.addPathPatterns("/**");
		}
		
		// Message Converter
		
		// View Resolver
		
		@Bean
		public ViewResolver viewResolver() {
			InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
			
			viewResolver.setViewClass(JstlView.class);
			viewResolver.setPrefix("/WEB-INF/views/");
			viewResolver.setSuffix(".jsp");
			
			return viewResolver;
		}
		
		// Message Converter
		@Bean
		public StringHttpMessageConverter stringHttpMessageConverter() { 
			StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
//			List<MediaType> list = new ArrayList<>();
//			list.add(new MediaType("text", "html", Charset.forName("utf-8")));
			
			
			messageConverter.setSupportedMediaTypes(
					Arrays.asList(new MediaType("text", "html", Charset.forName("utf-8"))));
			
			return messageConverter;
		}
		
		@Bean
		public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
			// indent = 들여쓰기, dataFormat = 날짜 설정 등..
			Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
					.indentOutput(true)
					.dateFormat(new SimpleDateFormat("yyyy-mm-dd"));
			
			// Converter 안에 builder 필요.
			MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(builder.build());
			
			messageConverter.setSupportedMediaTypes(
					Arrays.asList(new MediaType("application", "json", Charset.forName("utf-8"))));
			
			return messageConverter;
		}
		
		@Override
		public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
			converters.add(stringHttpMessageConverter());
			converters.add(mappingJackson2HttpMessageConverter());
		}
		
		// DefaultServelt Handler
		// <!-- 서블릿 컨테이너(tomcat)의 DefaultServlet 위임(delegate) Handler -->
		
		// Resource Mapping
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry
			.addResourceHandler(env.getProperty("fileupload.resourceMapping"))
			.addResourceLocations("file:" + env.getProperty("fileupload.uploadLocation"));
			
			registry
			.addResourceHandler("assets/**")
			.addResourceLocations("classpath:com/poscoict/mysite/assets/");
			
			
		}
}
