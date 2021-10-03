package com.github.iahrari.springrestmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metaData());
	}
	
	private ApiInfo metaData() {
		var api = new ApiInfoBuilder();
		api.title("Simple rest api with Spring boot mvc");
		api.description("This project is based on " +
				"https://github.com/springframeworkguru/spring5-mvc-rest" +
				" with just a little tweaks.");
		api.contact(new Contact(
				"Iman Ahrari",
				"https://iahrari.github.io",
				"iahrari@pm.me"
		));
		return api.build();
	}
}
