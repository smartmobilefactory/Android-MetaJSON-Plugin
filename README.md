# Android-MetaJSON-Plugin

plugin to collect information about the project and save them as json files for easier read and analyse from a CI task.

Provides the following gradle tasks:

- ``` createClocJson ```: counts the lines of code using [AlDanial/cloc](https://github.com/AlDanial/cloc)
- ``` createDependenciesJson ```: collects all used dependencies
- ``` createProjectJson ```: collects information about the used sdk versions
- ``` createMetaJson ```: runs all the task above

Installation
------------

Add the following to your `build.gradle`:

```gradle

buildscript {
    repositories {
        // ...
        maven { url "https://jitpack.io" }
    }
    dependencies {
        classpath 'com.github.smartmobilefactory:Android-MetaJSON-Plugin:X.X'
    }
}

apply plugin: 'com.android.application'

// Make sure to apply this plugin *after* the Android plugin
apply plugin: 'com.smartmobilefactory.metajson'

```
