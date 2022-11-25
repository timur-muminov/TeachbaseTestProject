package buildsrc

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion

fun BaseExtension.commonAndroid() {
    configureDefaultConfig()
    configureCompileOptions()
}

private fun BaseExtension.configureDefaultConfig(){
    compileSdkVersion(AppConfig.compile_sdk_version)

    defaultConfig {
        minSdk = AppConfig.min_sdk
        targetSdk = AppConfig.target_sdk
        versionCode = AppConfig.version_code
        versionName = AppConfig.version_name
    }
}

private fun BaseExtension.configureCompileOptions(){
    compileOptions.sourceCompatibility = JavaVersion.VERSION_1_8
    compileOptions.targetCompatibility = JavaVersion.VERSION_1_8
}

