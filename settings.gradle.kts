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
rootProject.name = "EcommerceConcept"
include(":app")
include(":app-dependencies")


include(":entities:products")
include(":entities:product")
include(":entities:categories")
include(":entities:filters")
include(":entities:cart")

include(":features:common")
include(":features:home")
include(":features:product_detail")
include(":features:cart")
include(":features:favourites")
