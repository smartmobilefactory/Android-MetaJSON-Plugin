package com.smartmobilefactory.metajson.project

import com.smartmobilefactory.metajson.BaseMetaJsonTask
import com.smartmobilefactory.metajson.MetaJsonPlugin
import com.smartmobilefactory.metajson.toJson
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

open class ProjectJsonTask : BaseMetaJsonTask() {

    init {
        description = "Writes all project infos to a file \"Project.json\""
    }

    @TaskAction
    fun execute(inputs: IncrementalTaskInputs) {

        var minSdkVersion = 0
        var targetSdkVersion = 0
        var multiDex = false
        appVariants.all { variant ->
            if (variant.buildType.name == "release") {
                minSdkVersion = variant.mergedFlavor.minSdkVersion.apiLevel
                targetSdkVersion = variant.mergedFlavor.targetSdkVersion.apiLevel
                multiDex = variant.mergedFlavor.multiDexEnabled ?: false
            }
        }

        val compileSdkVersion = android.compileSdkVersion?.let { it.substring(it.length - 2, it.length).toIntOrNull() }
                ?: 0
        var gradleVersion = ""
        var kotlinVersion = ""

        val gradleVersionPattern = Regex("gradle-([0-9]+.*)\\.jar")
        val kotlinVersionPattern = Regex("kotlin-stdlib-([0-9]+.*)\\.jar")
        project.rootProject.buildscript.configurations.getByName("classpath").forEach { dependency ->

            gradleVersionPattern.matchEntire(dependency.name)?.let { result ->
                if (result.groupValues.size > 1) {
                    gradleVersion = result.groupValues[1]
                }
            }

            kotlinVersionPattern.matchEntire(dependency.name)?.let { result ->
                if (result.groupValues.size > 1) {
                    kotlinVersion = result.groupValues[1]
                }
            }
        }

        val projectData = ProjectData(
                metaJsonVersion = MetaJsonPlugin.VERSION,
                gradleVersion = gradleVersion,
                kotlinVersion = kotlinVersion,
                buildToolsVersion = android.buildToolsVersion,
                compileSdkVersion = compileSdkVersion,
                minSdkVersion = minSdkVersion,
                targetSdkVersion = targetSdkVersion
        )

        writeMetaJsonFile("Project.json", projectData.toJson())
    }

}
