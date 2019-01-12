package org.anderer.streamtest

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class KafkaProcessor {
    @KafkaListener(topics = ["source"], groupId = "group")
    @SendTo("target")
    fun process(x: String): String {
        if(x == "fail")
            throw RuntimeException("Fail")
        return x+"y"
    }
}