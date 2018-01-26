package com.smartmobilefactory.metajson.project

internal data class ProjectData(
        val metaJsonVersion: String,
        val minSdkVersion: Int,
        val targetSdkVersion: Int,
        val compileSdkVersion: Int,
        val buildToolsVersion: String,
        val gradleVersion: String,
        val kotlinVersion: String,
        val dataBinding: Boolean,
        val jumboMode: Boolean,
        val multiDex: Boolean
)
