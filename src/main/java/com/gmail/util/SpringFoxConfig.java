package com.gmail.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

	}
	
	public ApiInfo getApiInfo() {
		
	return	new ApiInfoBuilder().title("Gmail Application")
		 					.description("We are a team of 3 worked on creating Rest APIs for Mail Application. We worked on creating REST API and writing business logic for a GMail application. Our project performs fundamental operations of an email application, where our user's data is validated, mapped, processed with business logic & persisted in the database.")
		 					.build();
		
	}
}
