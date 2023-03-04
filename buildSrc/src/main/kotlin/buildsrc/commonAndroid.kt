package buildsrc

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.dsl.DependencyHandler

fun BaseExtension.commonAndroid() {
    configureDefaultConfig()
    configureCompileOptions()
}


fun DependencyHandler.implementations(vararg dependencyNotation: Any) {
    dependencyNotation.forEach { add("implementation", it) }
}

fun DependencyHandler.apis(vararg dependencyNotation: Any) {
    dependencyNotation.forEach { add("api", it) }
}


private fun BaseExtension.configureDefaultConfig() {
    compileSdkVersion(AppConfig.compile_sdk_version)

    defaultConfig {
        minSdk = AppConfig.min_sdk
        targetSdk = AppConfig.target_sdk
        versionCode = AppConfig.version_code
        versionName = AppConfig.version_name
    }
}

private fun BaseExtension.configureCompileOptions() {
    compileOptions.sourceCompatibility = JavaVersion.VERSION_1_8
    compileOptions.targetCompatibility = JavaVersion.VERSION_1_8
}

