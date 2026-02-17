plugins {
    id("com.s.gradle.android.data")
    id("kotlinx-serialization")
}

android {
    namespace = "com.solutionium.data.network"

    defaultConfig {

        buildConfigField("String", "BASE_URL", "\"https://qeshminora.com/\"")
        buildConfigField("String", "CONSUMER_KEY", "\"${System.getenv("WOO_CONSUMER_KEY")}\"")
        buildConfigField("String", "CONSUMER_SECRET", "\"${System.getenv("WOO_CONSUMER_SECRET")}\"")

    }


    buildFeatures {
        buildConfig = true
    }

}

dependencies {

    //implementation(project(":data:local"))
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlinx.serilization.converter)
    implementation(libs.logging.interceptor) // Use the latest version


}