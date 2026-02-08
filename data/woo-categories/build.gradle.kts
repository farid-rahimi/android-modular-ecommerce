plugins {
    id("com.s.gradle.android.data")
}

android {
    namespace = "com.solutionium.data.woo.categories"
}

dependencies {
    implementation(project(":data:api:woo"))
}