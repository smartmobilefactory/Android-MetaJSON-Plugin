package com.smartmobilefactory.metajson.cloc

import com.smartmobilefactory.metajson.BaseMetaJsonTask
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs
import java.nio.charset.Charset

open class ClocJsonTask: BaseMetaJsonTask() {

    init {
        description = "Writes all used dependencies to a file \"Dependencies.json\""
    }

    @TaskAction
    fun execute(inputs: IncrementalTaskInputs) {
        val result = Runtime.getRuntime().exec("/usr/local/bin/cloc --vcs=git --json").apply {
            waitFor()
        }.inputStream
                .bufferedReader(Charset.defaultCharset())
                .use { it.readText() }

        val json = JsonSlurper().parseText(result) as MutableMap<*, *>
        json.remove("header")

        writeMetaJsonFile("Cloc.json", JsonOutput.prettyPrint(JsonOutput.toJson(json)))
    }

}