package com.cd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI( @Value("${app.title}") String appTitle,
			@Value("${app.description}") String appDescription,
			@Value("${app.version}") String appVersion) {
		return new OpenAPI().info(
				new Info()
				.title(appTitle)
				.version(appVersion)
				.description(appDescription));
	}
}
