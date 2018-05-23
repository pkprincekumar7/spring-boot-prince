package com.login.listener.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
@EnableKafka
public class ReceiverConfig {

  @Value("${kafka.bootstrap-servers}")
  private String bootstrapServers;
  
  @Value("${kafka.enable-auto-commit}")
  private String enableAutoCommit;
  
  @Value("${kafka.auto-commit-interval-ms}")
  private String autoCommitIntervalMs;
  
  @Value("${kafka.session-timeout-ms}")
  private String sessionTimeoutMs;
  
  @Value("${kafka.group-id}")
  private String groupId;
  
  @Value("${kafka.auto-offset-reset}")
  private String autoOffsetReset;

  @Bean
  public Map<String, Object> consumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    // list of host:port pairs used for establishing the initial connections to the Kafka cluster
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMs);
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    // allows a pool of processes to divide the work of consuming and processing records
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    // automatically reset the offset to the earliest offset
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);

    return props;
  }

  @Bean
  public ConsumerFactory<String, String> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());

    return factory;
  }

  @Bean
  public Receiver receiver() {
    return new Receiver();
  }
}