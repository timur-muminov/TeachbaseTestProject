import buildsrc.apis
import buildsrc.implementations

plugins {
    id("buildsrc.application")
    id("androidx.navigation.safeargs")
}
android.namespace = "com.teachbasetestproject.app"


dependencies {
    implementations(
        libs.appcompat,
        libs.material,
        libs.kotlin.coroutines.android,
        libs.fragment,
        libs.glide,
        libs.kotlin.reflect,
        libs.simple.rating.bar,
        libs.navigation.fragment,
        libs.navigation.ui
    )
    apis(projects.appDependencies)
}

