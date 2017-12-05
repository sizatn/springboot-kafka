package com.sizatn.springbootkafka.configuration;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.sizatn.springbootkafka.kafka.KafkaConsumer;

/**
 * kafka消费者配置
 *
 * @author
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value("${kafka.consumer.bootstrap-servers}")
	private String servers;

	@Value("${kafka.consumer.group-id}")
	private String groupId;

	@Value("${kafka.consumer.enable-auto-commit}")
	private boolean enableAutoCommit;

	@Value("${kafka.consumer.auto-commit-interval}")
	private String autoCommitInterval;

	@Value("${kafka.consumer.session-timeout-ms}")
	private String sessionTimeout;

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
		propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
		propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
		propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
		propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.CLIENT_ID_CONFIG, getUUID() + "_" + getIp());
		// 默认是latest，即从最新的开始消费
		propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return propsMap;
	}

	@Bean
	public KafkaConsumer kafkaConsumer() {
		return new KafkaConsumer();
	}

	/**
	 * uuid
	 * 
	 * @return
	 */
	private String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取IP
	 * 
	 * @return
	 */
	private String getIp() {
		String ip;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			ip = "unknown";
		}
		return ip;
	}

}
