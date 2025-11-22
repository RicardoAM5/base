package com.ram.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthifyApplication.class, args);
        System.out.println(" Ruta de la API: http://localhost:8080/ \n http://localhost:8080/api/v1.0/swagger-ui/index.html" );

	}

}
