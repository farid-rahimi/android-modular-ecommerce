plugins {
    id("com.s.gradle.android.data")
}

android {
    namespace = "com.solutionium.data.config"
}

dependencies {

    implementation(project(":data:api:woo"))

}