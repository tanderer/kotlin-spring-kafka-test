package org.anderer.streamtest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StreamtestApplication

fun main(args: Array<String>) {
	runApplication<StreamtestApplication>(*args)
}

