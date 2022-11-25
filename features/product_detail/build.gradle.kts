plugins {
    id("buildsrc.library")
}

android.namespace = "com.ecommerceconcept.product_detail"

dependencies {
    api(buildsrc.Dependencies.kotlin_coroutines_android)
    api(buildsrc.Dependencies.koin_android)
    api(buildsrc.Dependencies.viewmodel_lifecycle)
    api(buildsrc.Dependencies.moko_errors)
    api(buildsrc.Dependencies.moko_mvvm)
}

dependencies {
    api(projects.entities.product)

    api(projects.features.cart)
    api(projects.features.favourites)
    implementation(projects.features.common)
}
