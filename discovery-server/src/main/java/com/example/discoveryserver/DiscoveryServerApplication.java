package com.example.discoveryserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

//	private static Logger LOGGER = LoggerFactory.getLogger(DiscoveryServerApplication.class);
	
	public static void main(String[] args) {
		
//		LOGGER.info("Starting Discovery Server Aplication");
		
		SpringApplication.run(DiscoveryServerApplication.class, args);
	}

}
