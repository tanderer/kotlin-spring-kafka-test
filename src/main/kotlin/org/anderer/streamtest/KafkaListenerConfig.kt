//package org.anderer.streamtest
//
//import org.springframework.beans.factory.ObjectProvider
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
//import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer
//import org.springframework.boot.autoconfigure.kafka.KafkaProperties
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.kafka.annotation.EnableKafka
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
//import org.springframework.kafka.core.ConsumerFactory
//import org.springframework.kafka.core.KafkaTemplate
//import org.springframework.kafka.listener.AfterRollbackProcessor
//import org.springframework.kafka.listener.ErrorHandler
//import org.springframework.kafka.support.converter.RecordMessageConverter
//import org.springframework.kafka.transaction.KafkaAwareTransactionManager
//
//@Configuration
//@ConditionalOnClass(EnableKafka::class)
//internal class KafkaListenerConfig(
//        private val properties: KafkaProperties,
//        private val messageConverter: ObjectProvider<RecordMessageConverter>,
//        private val kafkaTemplate: ObjectProvider<KafkaTemplate<Any, Any>>,
//        private val transactionManager: ObjectProvider<KafkaAwareTransactionManager<Any, Any>>,
//        private val errorHandler: ObjectProvider<ErrorHandler>,
//        private val afterRollbackProcessor: ObjectProvider<AfterRollbackProcessor<Any, Any>>) {
////    private val messageConverter: RecordMessageConverter
////    private val kafkaTemplate: KafkaTemplate<Any, Any>
////    private val transactionManager: KafkaAwareTransactionManager<Any, Any>
////    private val errorHandler: ErrorHandler
////    private val afterRollbackProcessor: AfterRollbackProcessor<Any, Any>
//
////    init {
////        this.messageConverter = messageConverter.ifUnique
////        this.kafkaTemplate = kafkaTemplate.ifUnique
////        this.transactionManager = kafkaTransactionManager.ifUnique
////        this.errorHandler = errorHandler.ifUnique
////        this.afterRollbackProcessor = afterRollbackProcessor.ifUnique
////    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    fun kafkaListenerContainerFactoryConfigurer(): ConcurrentKafkaListenerContainerFactoryConfigurer {
//        val configurer = ConcurrentKafkaListenerContainerFactoryConfigurer()
//        configurer.setKafkaProperties(this.properties)
//        configurer.setMessageConverter(this.messageConverter)
//        configurer.setReplyTemplate(this.kafkaTemplate)
//        configurer.setTransactionManager(this.transactionManager)
//        configurer.setErrorHandler(this.errorHandler)
//        configurer.setAfterRollbackProcessor(this.afterRollbackProcessor)
//        return configurer
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(name = arrayOf("kafkaListenerContainerFactory"))
//    fun kafkaListenerContainerFactory(configurer: ConcurrentKafkaListenerContainerFactoryConfigurer, kafkaConsumerFactory: ConsumerFactory<Any, Any>): ConcurrentKafkaListenerContainerFactory<*, *> {
//        val factory = ConcurrentKafkaListenerContainerFactory()
//        configurer.configure(factory, kafkaConsumerFactory)
//        return factory
//    }
//
//    @Configuration
//    @EnableKafka
//    @ConditionalOnMissingBean(name = arrayOf("org.springframework.kafka.config.internalKafkaListenerAnnotationProcessor"))
//    protected class EnableKafkaConfiguration protected constructor()
//}
