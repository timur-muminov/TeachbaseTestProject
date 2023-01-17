plugins {
    id("buildsrc.library")
}

android.namespace = "com.teachbasetestproject.home"

dependencies {
    implementation(buildsrc.Dependencies.kotlin_coroutines_android)
    api(buildsrc.Dependencies.koin_android)
    api(buildsrc.Dependencies.viewmodel_lifecycle)
    api(buildsrc.Dependencies.moko_errors)
    api(buildsrc.Dependencies.moko_mvvm)
}

dependencies {
    api(projects.entities.movie)
    api(projects.entities.filter)

    implementation(projects.features.common)
}
