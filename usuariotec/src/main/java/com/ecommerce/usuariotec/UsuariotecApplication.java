package com.ecommerce.usuariotec;

import com.ecommerce.usuariotec.security.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(JwtProperties.class)
public class UsuariotecApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuariotecApplication.class, args);
	}

}
