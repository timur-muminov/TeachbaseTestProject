plugins {
    id("buildsrc.application")
    id("androidx.navigation.safeargs")
}
android.namespace = "com.ecommerceconcept.app"


dependencies {
    implementation(buildsrc.Dependencies.appcompat)
    implementation(buildsrc.Dependencies.material)
    implementation(buildsrc.Dependencies.kotlin_coroutines_android)
    implementation(buildsrc.Dependencies.fragment)
    implementation(buildsrc.Dependencies.glide)
    implementation(buildsrc.Dependencies.kotlin_reflect)
    implementation(buildsrc.Dependencies.simple_rating_bar)
    implementation(buildsrc.Dependencies.navigation_fragment)
    implementation(buildsrc.Dependencies.navigation_ui)
}

dependencies {
    api(projects.appDependencies)
}