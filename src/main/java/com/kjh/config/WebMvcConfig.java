package com.kjh.config;

import com.kjh.interceptor.ManageInterceptor;
import com.kjh.interceptor.UserMenuSessionInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private static final Logger log = LoggerFactory.getLogger(WebMvcConfigurer.class);

	private static final List<String> URL_PATTERNS = Arrays.asList("/management/**");

	@Autowired
	private ManageInterceptor manageInterceptor;

	@Autowired
	private UserMenuSessionInterceptor userMenuSessionInterceptor;

	@Value("${spring.servlet.multipart.location}")
	private String noticeUploadPath;

	@Value("${messageservice.resourcepath}")
	private String resourcePath;

	@Value("${spring.profiles.active}")
	private String profile;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.info("resourcePath : " + resourcePath);
		log.info("noticeUploadPath : " + noticeUploadPath);
		String uploadroot = "file:////";

		String OS = System.getProperty("os.name").toLowerCase();

		if ("WIN".equals(OS)) {
			//윈도우인 경우 로컬 패스에 / 기호를 추가한다.
			uploadroot = "//";
		}

		registry.addResourceHandler(resourcePath + "**")
			.addResourceLocations(uploadroot.concat(noticeUploadPath));
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userMenuSessionInterceptor)
			.addPathPatterns("/**");
		registry.addInterceptor(manageInterceptor)
			.addPathPatterns(URL_PATTERNS)
			.excludePathPatterns("/management/main");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public RestTemplate restTemplate() {
		HttpClient httpClient = HttpClientBuilder.create()
			.setMaxConnTotal(100) 				//최대 오픈되는 커넥션 수, 연결을 유지할 최대 숫자
			.setMaxConnPerRoute(30) 			//IP, 포트 1쌍에 대해 수행할 커넥션 수, 특정 경로당 최대 숫자
			.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(5000);			// 읽기시간초과, ms
		factory.setConnectTimeout(3000);		// 연결시간초과, ms
		factory.setHttpClient(httpClient); 		// 동기실행에 사용될 HttpClient 세팅

		return new RestTemplate(factory);
	}

}

