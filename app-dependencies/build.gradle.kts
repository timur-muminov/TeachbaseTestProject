plugins {
    id("buildsrc.library")
}

android.namespace = "com.ecommerceconcept.app_dependencies"

dependencies {
    implementation(buildsrc.Dependencies.kotlin_coroutines_android)
    implementation(buildsrc.Dependencies.retrofit)
    implementation(buildsrc.Dependencies.gson_converter)
    implementation(buildsrc.Dependencies.okhttp_interceptor)
}

dependencies {
    api(projects.features.home)
    api(projects.features.common)
    api(projects.features.productDetail)
    api(projects.features.cart)
    api(projects.features.favourites)
}

