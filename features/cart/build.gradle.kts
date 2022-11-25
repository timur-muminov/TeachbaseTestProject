plugins {
    id("buildsrc.library")
}

android.namespace = "com.ecommerceconcept.entity.cart"

dependencies {
    api(buildsrc.Dependencies.kotlin_coroutines_android)
    api(buildsrc.Dependencies.koin_android)
    api(buildsrc.Dependencies.viewmodel_lifecycle)
}

dependencies {
    api(projects.entities.cart)

    implementation(projects.features.common)
}
