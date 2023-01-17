plugins {
    id("buildsrc.library")
}
android.namespace = "com.teachbasetestproject.common"

dependencies {
    implementation(buildsrc.Dependencies.kotlin_coroutines_android)
    api(buildsrc.Dependencies.koin_android)
}