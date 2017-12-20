package com.sizatn.springbootkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		// kafka安全认证
		System.getProperties().setProperty("java.security.auth.login.config", "/data/kafka_client_jass.conf");
		
		SpringApplication.run(Application.class, args);
	}

}
