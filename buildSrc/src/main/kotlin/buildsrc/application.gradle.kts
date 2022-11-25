package buildsrc

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    commonAndroid()

    buildFeatures {
        viewBinding = true
    }
}
