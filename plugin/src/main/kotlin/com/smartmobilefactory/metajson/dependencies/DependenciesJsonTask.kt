package com.smartmobilefactory.metajson.dependencies

import com.smartmobilefactory.metajson.BaseMetaJsonTask
import groovy.lang.Closure
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

open class DependenciesJsonTask : BaseMetaJsonTask() {

    init {
        description = "Writes all used dependencies to a file \"Dependencies.json\""
    }

    @TaskAction
    fun execute(inputs: IncrementalTaskInputs) {
        println("OUTPUT")
        println(project.configurations.toString())
        val all = project.configurations.findAll(Closure.IDENTITY)

        all.forEach { println(it) }

        val deps = project.configurations.getByName("compile").incoming.resolutionResult.allDependencies.map {
            it.requested.displayName
        }
        deps.forEach { println(it) }
    }

}