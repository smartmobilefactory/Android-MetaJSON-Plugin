package com.smartmobilefactory.metajson.project

internal data class ProjectData(
        val minSdkVersion: String,
        val targetSdkVersion: String,
        val compileSdkVersion: String,
        val buildToolsVersion: String,
        val gradleVersion: String,
        val kotlinVersion: String,
        val dataBinding: Boolean,
        val jumboMode: Boolean,
        val multiDex: Boolean
)