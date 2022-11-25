plugins {
    id("buildsrc.library")
}
android.namespace = "com.ecommerceconcept.common"

dependencies {
    implementation(buildsrc.Dependencies.kotlin_coroutines_android)
    api(buildsrc.Dependencies.koin_core)
    api(buildsrc.Dependencies.koin_android)
}