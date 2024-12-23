package org.burgas.notificationservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.burgas.notificationservice.dto.PaymentMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public Map<String, Object> consumerConfig() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                ConsumerConfig.GROUP_ID_CONFIG, "payment-notification-group-id",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
                JsonDeserializer.TYPE_MAPPINGS,
                "org.burgas.paymentservice.dto.PaymentMessage:org.burgas.notificationservice.dto.PaymentMessage"
        );
    }

    @Bean
    public ConsumerFactory<String, PaymentMessage> paymentMessageConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentMessage> paymentMessageKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentMessageConsumerFactory());
        return factory;
    }

}
