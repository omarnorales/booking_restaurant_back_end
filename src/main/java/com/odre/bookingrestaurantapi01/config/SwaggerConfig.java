package com.odre.bookingrestaurantapi01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	/*
	 * Permite levantar todos los servicios detectados en el paquete com.odre.bookingrestaurantapi01
	 */

	// access via http://localhost:8080/swagger-ui/
		
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.odre.bookingrestaurantapi01")).paths(PathSelectors.any())
				.build();
	}

}
