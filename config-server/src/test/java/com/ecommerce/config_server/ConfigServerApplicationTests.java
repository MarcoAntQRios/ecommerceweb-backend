package com.ecommerce.config_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.cloud.config.server.enabled=false",
		"eureka.client.enabled=false",
		"spring.cloud.discovery.enabled=false"
})class ConfigServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
