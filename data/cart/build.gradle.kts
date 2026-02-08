plugins {
    id("com.s.gradle.android.data")
}

android {
    namespace = "com.solutionium.data.cart"
}

dependencies {
    implementation(project(":data:database"))
}