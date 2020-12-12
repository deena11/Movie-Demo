package com.example.kafkapublishserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author M1053559
 * @description kafka server 
 */
@RestController
@RequestMapping("/kafka")
public class PublishController {
	
	private Logger logger = LoggerFactory.getLogger(PublishController.class);

	private String message="";
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	private static final String TOPIC ="KafkaExample";
	
	private static final String SEARCH_LOAD_TOPIC ="PlayTopic";
	
	
	
	/**
	 * @param message
	 * @return
	 */
	@GetMapping("/publish/{message}")
	public String publishMessage(@PathVariable("message") String message) {
		try {
		logger.info("Kafka Pushlisher is called and msg is pushed into kafkatopic");
	
		kafkaTemplate.send(TOPIC,message);
		message="Published Successfully";
		}
		catch(Exception ex){
			logger.error("failed to publish cause- "+ex.getMessage());
			message="failed to publish cause- "+ex.getMessage();
		}
		return message;
	}
	
	/**
	 * @param message
	 * @return
	 */
	@GetMapping("/publish/play/{message}")
	public String publishMessagetoSearchService(@PathVariable("message") String message) {
		try {
			logger.info("Kafka Pushlisher is called and msg is pushed into kafkatopic");
		
			kafkaTemplate.send(SEARCH_LOAD_TOPIC,message);
			message="Published Successfully";
			}
			catch(Exception ex){
				logger.error("failed to publish cause- "+ex.getMessage());
				message="failed to publish cause- "+ex.getMessage();
			}
			return message;
	}
	
}
