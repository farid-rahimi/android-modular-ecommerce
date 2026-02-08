plugins {
    id("com.s.gradle.android.feature")
    id("com.s.gradle.android.library.compose")
}

android {
    namespace = "com.solutionium.feature.orders"
}

dependencies {
    implementation(project(":data:model"))
    implementation(project(":domain:order"))

    implementation(libs.paging.compose)
    api(libs.androidx.material3)


}