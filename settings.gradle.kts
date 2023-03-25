@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    versionCatalogs {
        create("libs") {
            from(files("buildSrc/src/main/kotlin/buildsrc/Libraries.toml"))
        }
    }
}
rootProject.name = "TeachbaseTestProject"
include(":app")
include(":app-dependencies")
include(":entities:movie")
include(":entities:filter")


include(":features:common")
include(":features:home")
include(":features:movie_detail")
include(":features:search")
include(":features:filter")
