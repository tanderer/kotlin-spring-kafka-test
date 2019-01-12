package org.anderer.streamtest

import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.record.Record
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.context.annotation.Import
import org.springframework.kafka.core.*
import org.springframework.kafka.listener.*
import org.springframework.kafka.listener.ContainerProperties.AckMode
import org.springframework.kafka.listener.adapter.RetryingMessageListenerAdapter
import java.lang.Exception


@Configuration
public class KafkaErrorHandlerConfig {

//    @Bean
//    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> {
//       val factory:  ConcurrentKafkaListenerContainerFactory<Integer, String> =
//         ConcurrentKafkaListenerContainerFactory();
//        factory.setConsumerFactory(consumerFactory());
//        factory.setConcurrency(3);
//        factory.getContainerProperties().setPollTimeout(3000);
//        return factory;
//    }
//
//    @Bean
//    fun consumerFactory(): ConsumerFactory<Integer, String> {
//        return DefaultKafkaConsumerFactory(consumerConfigs());
//    }
//
//    @Bean
//    fun consumerConfigs(): Map<String, String>  {
//        return mapOf(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaBroker);
//    }



//    public ConcurrentKafkaListenerContainerFactoryConfigurer kafkaListenerContainerFactoryConfigurer() {
//
//    }
//
//    fun producerConfigs(): Map<String, String>  {
//        return mapOf(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaBroker);
//    }
//
//    //    @Bean
//    fun producerFactory(): ProducerFactory<Any, Any> {
//        return DefaultKafkaProducerFactory(producerConfigs());
//    }
//
//    fun errorHandlingContainerFactory(consumerFactory: ConsumerFactory<Integer,String>, errorHandler: SeekToCurrentErrorHandler): ConcurrentKafkaListenerContainerFactory<Integer, String> {
//        val factory = ConcurrentKafkaListenerContainerFactory<Integer, String>()
//        factory.setConsumerFactory(consumerFactory)
//        factory.getContainerProperties().setAckOnError(false)
//        factory.getContainerProperties().setAckMode(AckMode.RECORD)
//        factory.setErrorHandler(errorHandler)
//        return factory
//    }

        @Bean
    fun errorHandler(kafkaTemplate: KafkaTemplate<Any, Any>): ErrorHandler {
//        val producerFactory = producerFactory()
//
//        val template = KafkaTemplate(producerFactory)
        val function: (ConsumerRecord<*, *>, Exception) -> TopicPartition = { r: ConsumerRecord<*,*>, _: Exception ->
            TopicPartition(r.topic() + ".dlq", r.partition())
        }
        val recoverer = DeadLetterPublishingRecoverer(kafkaTemplate, function)
        val errorHandler: ErrorHandler = SeekToCurrentErrorHandler (recoverer, 3)
        return errorHandler
    }



//    @Bean
//    fun errorHandler(kafkaTemplate: KafkaTemplate<Any, Any>): DefaultAfterRollbackProcessor<Any, Any> {
////        val producerFactory = producerFactory()
////
////        val template = KafkaTemplate(producerFactory)
//        val function: (ConsumerRecord<*, *>, Exception) -> TopicPartition = { r: ConsumerRecord<*,*>, _: Exception ->
//            TopicPartition(r.topic() + ".dlq", r.partition())
//        }
//        val recoverer = DeadLetterPublishingRecoverer(kafkaTemplate, function)
//        val errorHandler: DefaultAfterRollbackProcessor<Any, Any> = DefaultAfterRollbackProcessor(recoverer, 3)
//        return errorHandler
//    }

}