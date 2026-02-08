plugins {
    id("com.s.gradle.android.data")
    id("kotlinx-serialization")
}

android {
    namespace = "com.solutionium.data.database"
}

dependencies {

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.kotlinx.serialization.json)
}