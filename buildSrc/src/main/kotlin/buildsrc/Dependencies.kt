package buildsrc

object Dependencies {
    const val build_gradle = "com.android.tools.build:gradle:${Versions.build_gradle}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_gradle_plugin}"

    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"

    const val material = "com.google.android.material:material:${Versions.material}"

    const val kotlin_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines_core}"
    const val kotlin_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines_android}"
    const val kotlin_reflect =  "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin_reflect}"

    const val viewmodel_lifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewmodel_lifecycle}"

    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_kapt = "com.github.bumptech.glide:compiler:${Versions.glide_kapt}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson_converter = "com.squareup.retrofit2:converter-gson:${Versions.gson_converter}"
    const val okhttp_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_interceptor}"

    const val koin_core = "io.insert-koin:koin-core:${Versions.koin_core}"
        const val koin_android = "io.insert-koin:koin-android:${Versions.koin_android}"


    const val moko_errors = "dev.icerock.moko:errors:${Versions.moko_errors}"

    const val moko_mvvm = "dev.icerock.moko:mvvm-core:${Versions.moko_mvvm}"

    const val simple_rating_bar = "com.github.ome450901:SimpleRatingBar:${Versions.simple_rating_bar}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation_ui}"
    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation_fragment}"

}

