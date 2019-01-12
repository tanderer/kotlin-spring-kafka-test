package org.anderer.streamtest

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.KafkaException
import org.apache.kafka.common.TopicPartition
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ContainerAwareErrorHandler
import org.springframework.kafka.listener.MessageListenerContainer
import org.springframework.stereotype.Component

//from https://stackoverflow.com/questions/49507709/dead-letter-queue-dlq-for-kafka-with-spring-kafka
//@Component
//class DlqErrorHandler internal constructor(private val kafkaTemplate: KafkaTemplate<Any, Any>) : ContainerAwareErrorHandler {
//
//    override fun handle(thrownException: Exception, records: List<ConsumerRecord<*, *>>?, consumer: Consumer<*, *>?, container: MessageListenerContainer?) {
//        val record = records!![0]
//        try {
//            kafkaTemplate.send(record.topic() + ".dlq", record.key(), record.value())
//            consumer!!.seek(TopicPartition(record.topic(), record.partition()), record.offset() + 1)
//            // Other records may be from other partitions, so seek to current offset for other partitions too
//            // ...
//        } catch (e: Exception) {
//            consumer!!.seek(TopicPartition(record.topic(), record.partition()), record.offset())
//            // Other records may be from other partitions, so seek to current offset for other partitions too
//            // ...
//            throw KafkaException("Seek to current after exception", thrownException)
//        }
//
//    }
//}