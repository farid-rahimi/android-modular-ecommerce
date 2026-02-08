plugins {
    id("com.s.gradle.android.data")
}

android {
    namespace = "com.solutionium.data.local"
}

dependencies {

    implementation(libs.androidx.security.crypto) // Or latest stable/alpha version
}