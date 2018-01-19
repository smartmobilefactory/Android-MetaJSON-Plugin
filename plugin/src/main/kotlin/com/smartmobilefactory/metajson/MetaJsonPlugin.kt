package com.smartmobilefactory.metajson

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet
import org.gradle.api.Plugin
import org.gradle.api.Project

class MetaJsonPlugin : Plugin<Project> {

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

    private fun <T : BaseVariant> configureAndroid(project: Project, variants: DomainObjectSet<T>) {

    }
}
