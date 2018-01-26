package com.smartmobilefactory.metajson

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.ApplicationVariant
import com.smartmobilefactory.metajson.cloc.ClocJsonTask
import com.smartmobilefactory.metajson.dependencies.DependenciesJsonTask
import com.smartmobilefactory.metajson.project.ProjectJsonTask
import org.gradle.api.DomainObjectSet
import org.gradle.api.Plugin
import org.gradle.api.Project

internal const val META_JSON_GROUP = "MetaJSON"
internal const val CREATE_META_JSON_TASK = "createMetaJson"
internal const val CLOC_TASK = "createClocJson"
internal const val DEPENDENCIES_TASK = "createDependenciesJson"
internal const val PROJECT_TASK = "createProjectJson"

open class MetaJsonPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.afterEvaluate {
            project.plugins.all {
                when (it) {
                    is AppPlugin -> configureAndroid(project,
                            project.extensions.getByType(AppExtension::class.java).applicationVariants)
                    is LibraryPlugin -> {
                        /* Library projects not supported */
                    }
                }
            }
        }

    }

    private fun configureAndroid(project: Project, variant: DomainObjectSet<ApplicationVariant>) {

        val android: BaseExtension = project.extensions.getByName("android") as BaseExtension

        val configureBaseTask: (BaseMetaJsonTask) -> Unit = { task ->
            task.android = android
            task.appVariants = variant
        }

        val clocJsonTask = project.tasks.create(CLOC_TASK, ClocJsonTask::class.java).also(configureBaseTask)

        val dependencyJsonTask = project.tasks.create(DEPENDENCIES_TASK, DependenciesJsonTask::class.java).also(configureBaseTask)

        val projectJsonTask = project.tasks.create(PROJECT_TASK, ProjectJsonTask::class.java).also(configureBaseTask)

        project.tasks.create(CREATE_META_JSON_TASK, {
            it.apply {
                group = META_JSON_GROUP
                dependsOn(projectJsonTask)
                dependsOn(dependencyJsonTask)
                dependsOn(clocJsonTask)
            }
        })

    }

    companion object {
        internal val VERSION = "0.1"
    }
}

