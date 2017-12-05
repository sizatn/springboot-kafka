package com.sizatn.springbootkafka.kafka;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;

public class KafkaConsumer {

	private final static Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
	
	@KafkaListener(topics = "bayonet")
//	@KafkaListener(topicPartitions = {@TopicPartition(topic="bayonet", partitions={"0"})})
	public void processMessage(ConsumerRecord<String, String> record) {
		Optional<?> kafkaMessage = Optional.ofNullable(record.value());
		if (kafkaMessage.isPresent()) {
			Object message = kafkaMessage.get();
			System.out.println(message);
		}
	}
}
