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


    // Core and Android Engine
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android) // Or ktor-client-okhttp

    // Serialization (replaces Retrofit converters)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // Logging (replaces HttpLoggingInterceptor)
    implementation(libs.ktor.client.logging)


}