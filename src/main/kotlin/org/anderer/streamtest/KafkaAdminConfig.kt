package org.anderer.streamtest

import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.record.Record
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.context.annotation.Import
import org.springframework.kafka.core.*
import org.springframework.kafka.listener.SeekToCurrentErrorHandler
import org.springframework.kafka.listener.ContainerProperties.AckMode
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.ErrorHandler
import org.springframework.kafka.transaction.KafkaAwareTransactionManager
import org.springframework.kafka.transaction.KafkaTransactionManager
import java.lang.Exception


@Configuration
@EnableKafka
public class KafkaAdminConfig {
    @Bean
    fun sourceTopic(): NewTopic {
        return NewTopic("source", 1, 1.toShort())
    }

    @Bean
    fun targetTopic(): NewTopic {
        return NewTopic("target", 1, 1.toShort())
    }

    @Bean
    fun targetDlqTopic(): NewTopic {
        return NewTopic("source.dlq", 1, 1.toShort())
    }
}