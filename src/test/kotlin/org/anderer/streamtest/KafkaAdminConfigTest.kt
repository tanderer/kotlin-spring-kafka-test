package org.anderer.streamtest

import org.anderer.streamtest.transform.JacksonProcessor
import org.apache.kafka.clients.admin.AdminClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@EnableKafka
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [JacksonProcessor::class, KafkaAdminConfig::class, KafkaAutoConfiguration::class])
@DirtiesContext
@EmbeddedKafka(partitions = 1, controlledShutdown = false,
        brokerProperties = ["listeners=PLAINTEXT://localhost:3333", "port=3333"])
@TestPropertySource(properties = [ "spring.kafka.bootstrap-servers=\${spring.embedded.kafka.brokers}"])
class KafkaAdminConfigTest {
    @Autowired
    lateinit var kafkaAdmin: KafkaAdmin


    @Test
    fun itShouldCreateTopicForProcessor () {
        val adminClient = AdminClient.create(kafkaAdmin.config)
        val actualTopics = adminClient.listTopics().names().get()
        val expectedTopics = setOf(JacksonProcessor.INPUT, JacksonProcessor.OUTPUT, JacksonProcessor.INPUT + ".dlq")
        assertThat(actualTopics).containsAll(expectedTopics)
    }
}