package org.anderer.streamtest

import org.anderer.streamtest.transform.DataTransformation
import org.apache.kafka.clients.admin.AdminClient
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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.context.annotation.Import
import org.springframework.kafka.core.*
import org.springframework.kafka.listener.SeekToCurrentErrorHandler
import org.springframework.kafka.listener.ContainerProperties.AckMode
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.ErrorHandler
import org.springframework.kafka.transaction.KafkaAwareTransactionManager
import org.springframework.kafka.transaction.KafkaTransactionManager
import org.springframework.stereotype.Component
import java.lang.Exception
import javax.annotation.PostConstruct


@Component
@EnableKafka
public class KafkaAdminConfig {
    @Autowired
    lateinit var transformations: List<DataTransformation>

    @Autowired
    lateinit var kafkaAdmin: KafkaAdmin

    @PostConstruct
    fun setupTopics() {
        val admin = AdminClient.create(kafkaAdmin.config)
        val topics = transformations.flatMap {
            listOf(it.inputTopic, it.outputTopic, dlqName(it.inputTopic))
        }.map { NewTopic(it, 1, 1.toShort()) }
                .toList()
        admin.createTopics(topics)
    }

    private fun dlqName(inputTopic: String): String {
        return "$inputTopic.dlq"
    }
}