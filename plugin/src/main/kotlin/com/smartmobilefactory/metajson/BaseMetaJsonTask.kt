package com.smartmobilefactory.metajson

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.DefaultTask
import org.gradle.api.DomainObjectSet
import java.io.File

private const val METAJSON_DIR = ".MetaJSON"

abstract class BaseMetaJsonTask: DefaultTask() {

    init {
        group = META_JSON_GROUP
    }

    lateinit var variant : DomainObjectSet<ApplicationVariant>

    private fun createMetaJsonDir() {
        val dir = File(METAJSON_DIR)
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }

    protected fun writeMetaJsonFile(fileName: String, content: String) {
        createMetaJsonDir()
        File(METAJSON_DIR + "/" + fileName).writeText(content, Charsets.UTF_8)
    }

}
