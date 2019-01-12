package org.anderer.streamtest.transform

interface DataTransformation {
    val inputTopic: String
    val outputTopic: String

}