
plugins {
    id("buildsrc.library")
    id ("kotlin-kapt")
}

android.namespace = "com.teachbasetestproject.app_dependencies"

dependencies {
    implementation(buildsrc.Dependencies.kotlin_coroutines_android)
    implementation(buildsrc.Dependencies.kotlin_coroutines_core)
    implementation(buildsrc.Dependencies.retrofit)
    implementation(buildsrc.Dependencies.gson_converter)
    implementation(buildsrc.Dependencies.okhttp_interceptor)
    implementation(buildsrc.Dependencies.room_runtime)
    implementation(buildsrc.Dependencies.room_ktx)
    kapt(buildsrc.Dependencies.room_compiler)
}

dependencies {
    api(projects.features.home)
    api(projects.features.common)
    api(projects.features.movieDetail)
    api(projects.features.search)
    api(projects.features.filter)
}

