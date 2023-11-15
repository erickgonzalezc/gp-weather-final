package com.pfc2.weather.pruebatecnica;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PruebaTecnicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaTecnicaApplication.class, args);
	}
	@Bean
	public GroupedOpenApi groupedOpenApi(){
		return GroupedOpenApi.builder()
				.group("api")
				.group("sys")
				.packagesToScan("com.pfc2.weather.pruebatecnica.controller")
				.build();
	}

}
