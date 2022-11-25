buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()

        dependencies {
            classpath(buildsrc.Dependencies.kotlin_gradle_plugin)
            classpath(buildsrc.Dependencies.build_gradle)
        }
    }
}

plugins {
    id("androidx.navigation.safeargs") version "2.5.2" apply false
}

