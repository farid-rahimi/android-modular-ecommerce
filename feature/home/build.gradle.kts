plugins {
    id("com.s.gradle.android.feature")
    id("com.s.gradle.android.library.compose")
}

android {
    namespace = "com.solutionium.feature.home"
}

dependencies {

    implementation(project(":domain:woo-products"))
    implementation(project(":domain:woo-categories"))
    implementation(project(":domain:cart"))
    implementation(project(":domain:favorite"))
    implementation(project(":domain:config"))
    implementation(project(":domain:user"))

    implementation(project(":data:model"))

    api(libs.androidx.material3)
}