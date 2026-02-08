plugins {
    id("com.s.gradle.android.domain")
}

android {
    namespace = "com.solutionium.domain.user"
}

dependencies {

    implementation(project(":data:woo-user"))
}