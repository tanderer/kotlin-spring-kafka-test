package org.anderer.streamtest

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.kafka.core.*
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.KafkaMessageListenerContainer
import org.springframework.kafka.listener.MessageListener
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.rule.KafkaEmbedded
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.utils.ContainerTestUtils
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.annotation.DirtiesContext
import java.time.Duration
import java.util.concurrent.LinkedBlockingQueue


@EnableKafka
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [KafkaErrorHandlerConfig::class, KafkaConfig::class, KafkaAdminConfig::class, KafkaAutoConfiguration::class, KafkaProcessor::class])
@DirtiesContext
@EmbeddedKafka(partitions = 1, controlledShutdown = false,
        brokerProperties = ["listeners=PLAINTEXT://localhost:3333", "port=3333"])
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

    lateinit var pf: ProducerFactory<Integer, String>

    @Value("\${spring.embedded.kafka.brokers}")
    lateinit var value: String


    @BeforeEach
    private fun buildKafkaTemplate() {
        val senderProps = KafkaTestUtils.producerProps(embeddedKafka)
        pf = DefaultKafkaProducerFactory<Integer, String>(senderProps)

//        return KafkaTemplate(pf)
    }

    @Test
    fun test() {
        val createConsumer = consumerFactory.createConsumer("xx", "xx")
        createConsumer.subscribe(listOf("target"))

        val producer = producerFactory.createProducer()
        producer.send(ProducerRecord("source", "xx"))

        val records = createConsumer.poll(Duration.ofSeconds(5))
        createConsumer.unsubscribe()
        createConsumer.close()


        Assertions.assertTrue(!records.isEmpty)
    }


    @Test
    fun testFail() {
        val createConsumer = consumerFactory.createConsumer("xx2", "xx2")
        createConsumer.subscribe(listOf("source.dlq"))

        val producer = producerFactory.createProducer()
        producer.send(ProducerRecord("source", "fail"))

        val records = createConsumer.poll(Duration.ofSeconds(5))
        createConsumer.unsubscribe()
        createConsumer.close()


        Assertions.assertTrue(!records.isEmpty)
    }


}