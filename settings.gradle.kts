enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
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
