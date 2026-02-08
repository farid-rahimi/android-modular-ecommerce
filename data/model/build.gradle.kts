plugins {
    id("com.s.gradle.jvm.library")
    id("kotlinx-serialization")

}

dependencies {
    implementation(libs.kotlinx.serialization.json)

}
