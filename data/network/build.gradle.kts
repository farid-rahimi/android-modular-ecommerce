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
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") // Use the latest version

    val ktorVersion = "2.3.12" // Use the latest stable version

    // Core and Android Engine
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android) // Or ktor-client-okhttp

    // Serialization (replaces Retrofit converters)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // Logging (replaces HttpLoggingInterceptor)
    implementation(libs.ktor.client.logging)


}