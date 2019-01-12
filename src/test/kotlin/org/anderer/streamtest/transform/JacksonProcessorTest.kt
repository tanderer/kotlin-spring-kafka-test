package org.anderer.streamtest.transform

import org.anderer.streamtest.KafkaAdminConfig
import org.anderer.streamtest.KafkaConfig
import org.anderer.streamtest.KafkaErrorHandlerConfig
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.producer.ProducerRecord
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.kafka.core.*
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.annotation.DirtiesContext
import java.time.Duration


@EnableKafka
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [JacksonAutoConfiguration::class, KafkaErrorHandlerConfig::class, KafkaConfig::class, KafkaAdminConfig::class, KafkaAutoConfiguration::class, JacksonProcessor::class])
@DirtiesContext
@EmbeddedKafka(partitions = 1, controlledShutdown = false,
        brokerProperties = ["listeners=PLAINTEXT://localhost:3333", "port=3333", "transaction.state.log.replication.factor=1", "transaction.state.log.min.isr=1", "offsets.topic.replication.factor=1"])
@TestPropertySource(properties = [ "spring.kafka.bootstrap-servers=\${spring.embedded.kafka.brokers}"])
class KafkaConsumerTest {
    @Autowired
    lateinit var embeddedKafka: EmbeddedKafkaBroker

    @Autowired
    lateinit var kafkaListenerEndpointRegistry: KafkaListenerEndpointRegistry

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<Integer, String>

    @Autowired
    lateinit var consumerFactory: ConsumerFactory<Integer, String>

    @Autowired
    lateinit var producerFactory: ProducerFactory<Integer, String>


    @BeforeEach
    private fun setup() {
    }

    @AfterEach
    private fun teardown() {
    }

    @Test
    fun test() {
        val consumer = createConsumer(JacksonProcessor.OUTPUT)
        produceMessage(JacksonProcessor.INPUT, "{\"x\":\"y\"}")

        val records = consumer.poll(Duration.ofSeconds(5))
        Assertions.assertTrue(!records.isEmpty)
    }

    @Test
    fun testFail() {
        val consumer = createConsumer("${JacksonProcessor.INPUT}.dlq")
        produceMessage(JacksonProcessor.INPUT, "{\"x\":\"fail\"}")

        val records = consumer.poll(Duration.ofSeconds(5))
        Assertions.assertTrue(!records.isEmpty)
    }

    private fun produceMessage(topic: String, message: String) {
//        val producer = producerFactory.createProducer()
//        producer.send(ProducerRecord(topic, message))
        kafkaTemplate.executeInTransaction {
            it.send(ProducerRecord(topic, message))
        }
    }

    fun createConsumer(topic: String): Consumer<Integer, String> {
        val targetConsumer = consumerFactory.createConsumer(topic, topic)
        targetConsumer.subscribe(listOf(topic))
        return targetConsumer
    }



}