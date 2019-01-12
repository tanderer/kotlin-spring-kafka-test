package org.anderer.streamtest

import com.fasterxml.jackson.databind.ObjectMapper
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
import org.springframework.kafka.support.converter.MessageConverter
import org.springframework.kafka.support.converter.RecordMessageConverter
import org.springframework.kafka.transaction.KafkaAwareTransactionManager
import org.springframework.kafka.transaction.KafkaTransactionManager
import java.lang.Exception
import org.springframework.kafka.support.converter.StringJsonMessageConverter




@Configuration
@EnableKafka
//@Import(KafkaErrorHandlerConfig::class)
public class KafkaConfig {

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

//    @Bean
//    fun kafkaListenerContainerFactory(consumerFactory: ConsumerFactory<Integer,String>, errorHandler: ErrorHandler): ConcurrentKafkaListenerContainerFactory<Integer, String> {
//        val factory = ConcurrentKafkaListenerContainerFactory<Integer, String>()
//        factory.setConsumerFactory(consumerFactory)
//        factory.getContainerProperties().setAckOnError(false)
//        factory.getContainerProperties().setAckMode(AckMode.RECORD)
//        factory.setErrorHandler(errorHandler)
//        return factory
//    }

//    @Bean
//    fun transactionManager(producerFactory: ProducerFactory<Any, Any>): KafkaAwareTransactionManager<Any, Any> {
//        return KafkaTransactionManager(producerFactory)
//    }

//    @Bean
//    fun converter(): MessageConverter {
//        return StringJsonMessageConverter()
//    }

    @Bean
    fun converter2(objectMapper: ObjectMapper): RecordMessageConverter {
        return StringJsonMessageConverter(objectMapper)
    }

}