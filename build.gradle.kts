

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()

        dependencies {
            classpath(buildsrc.Dependencies.kotlin_gradle_plugin)
            classpath(buildsrc.Dependencies.build_gradle)
            classpath(buildsrc.Dependencies.navigation_safeargs_plugin)
        }
    }
}

