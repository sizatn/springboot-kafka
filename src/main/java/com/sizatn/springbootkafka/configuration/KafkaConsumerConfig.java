package com.sizatn.springbootkafka.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.sizatn.springbootkafka.kafka.KafkaConsumer;

/**
 * 
 * @desc kafka消费者配置
 * @author sizatn
 * @date Dec 20, 2017
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> propsMap = new HashMap<>();
		propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getConsumer().getBootstrapServers());
		propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
		propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.getConsumer().getEnableAutoCommit());
		propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,kafkaProperties.getConsumer().getAutoCommitInterval());
		propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 6000);
		propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// 默认是latest，即从最新的开始消费
		propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getConsumer().getAutoOffsetReset());
		// kafka安全认证
//		propsMap.put("security.protocol", "SASL_PLAINTEXT");
//		propsMap.put("sasl.mechanism", "PLAIN");
		return propsMap;
	}

	@Bean
	public KafkaConsumer kafkaConsumer() {
		return new KafkaConsumer();
	}

}
