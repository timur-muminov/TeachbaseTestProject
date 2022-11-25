plugins {
    id("buildsrc.library")
}

android.namespace = "com.ecommerceconcept.favourites"

dependencies {
    implementation(buildsrc.Dependencies.kotlin_coroutines_android)
}