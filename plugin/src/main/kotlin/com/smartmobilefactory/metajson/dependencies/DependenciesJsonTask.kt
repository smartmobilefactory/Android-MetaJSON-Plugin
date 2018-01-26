package com.smartmobilefactory.metajson.dependencies

import com.smartmobilefactory.metajson.BaseMetaJsonTask
import com.smartmobilefactory.metajson.toJson
import groovy.lang.Closure
import org.gradle.api.artifacts.result.DependencyResult
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

open class DependenciesJsonTask : BaseMetaJsonTask() {

    init {
        description = "Writes all used dependencies to a file \"Dependencies.json\""
    }

    private val dependencyPattern = Regex("(.+:.*):(.*)")

    @TaskAction
    fun execute(inputs: IncrementalTaskInputs) {
        val dependencies = project.configurations.findAll(Closure.IDENTITY)
                .flatMap {
                    try {
                        it.incoming.resolutionResult.allDependencies
                    } catch (e: Exception) {
                        setOf<DependencyResult>()
                    }
                }
                .map { it.requested.displayName }
                .distinct()
                .mapNotNull {
                    dependencyPattern.matchEntire(it)?.let { matchResult ->
                        if (matchResult.groupValues.size > 2) {
                            Dependency(name = matchResult.groupValues[1], version = matchResult.groupValues[2])
                        } else {
                            null
                        }
                    }
                }
                .toList()
        writeMetaJsonFile("Dependencies.json", dependencies.toJson())
    }

}