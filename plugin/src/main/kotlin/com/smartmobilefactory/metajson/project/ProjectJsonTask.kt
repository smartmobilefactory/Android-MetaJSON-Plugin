package com.smartmobilefactory.metajson.project

import com.smartmobilefactory.metajson.BaseMetaJsonTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

open class ProjectJsonTask: BaseMetaJsonTask() {

    init {
        description = "Writes all project infos to a file \"Project.json\""
    }

    @TaskAction
    fun execute(inputs: IncrementalTaskInputs) {

    }

}
