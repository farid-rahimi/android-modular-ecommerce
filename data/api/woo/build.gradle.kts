plugins {
    id("com.s.gradle.android.data")
}

android {
    namespace = "com.solutionium.data.api.woo"

    val appVersionName = rootProject.property("versionName") as String

    defaultConfig {
        // This makes the version available in the module's BuildConfig
        buildConfigField("String", "APP_VERSION_NAME", "\"$appVersionName\"")
    }

    buildFeatures {
        buildConfig = true
    }

}

dependencies {

    //implementation(libs.retrofit)

    //implementation(project(":data:network"))
    implementation(project(":shared"))

}