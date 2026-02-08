plugins {
    id("com.s.gradle.android.data")
}

android {
    namespace = "com.solutionium.data.woo.checkout"



}

dependencies {
    implementation(project(":data:api:woo"))
    implementation(project(":data:local"))
}