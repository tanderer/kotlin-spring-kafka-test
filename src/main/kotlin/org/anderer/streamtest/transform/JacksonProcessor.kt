package org.anderer.streamtest.transform

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class JacksonProcessor : DataTransformation {
    override val inputTopic = INPUT
    override val outputTopic = OUTPUT

    @KafkaListener(topics = [INPUT], groupId = "group")
    @SendTo(OUTPUT)
    fun process(input: Input): Output {
        if(input.x == "fail")
            throw RuntimeException("fail")
        return Output(input.x)
    }

    data class Input(val x: String)
    data class Output(val y:String)

    companion object {
        const val INPUT = "jacksonSource"
        const val OUTPUT = "jacksonTarget"
    }
}