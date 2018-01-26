package com.smartmobilefactory.metajson

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.api.ApplicationVariant
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import org.gradle.api.DefaultTask
import org.gradle.api.DomainObjectSet
import java.io.File

private const val METAJSON_DIR = ".MetaJSON"

internal val moshi by lazy {
    Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
}

internal inline fun <reified T> T.toJson(): String {
    return moshi.adapter(T::class.java).indent("    ").toJson(this)
}

abstract class BaseMetaJsonTask : DefaultTask() {

    init {
        group = META_JSON_GROUP
        outputs.upToDateWhen { false }
    }

    lateinit var android: BaseExtension
    lateinit var appVariants: DomainObjectSet<ApplicationVariant>

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
