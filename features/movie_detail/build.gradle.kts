plugins {
    id("buildsrc.library")
}

android.namespace = "com.teachbasetestproject.movie_detail"

dependencies {
    api(buildsrc.Dependencies.kotlin_coroutines_android)
    api(buildsrc.Dependencies.koin_android)
    api(buildsrc.Dependencies.viewmodel_lifecycle)
    api(buildsrc.Dependencies.moko_errors)
    api(buildsrc.Dependencies.moko_mvvm)
}

dependencies {
    implementation(projects.entities.movie)

    implementation(projects.features.common)
}
