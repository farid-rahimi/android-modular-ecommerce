plugins {
    id("com.s.gradle.android.library")
    id("com.s.gradle.android.library.compose")
}

android {
    namespace = "com.solutionium.core.ui.common"

}

dependencies {

    implementation(project(":data:model"))
    implementation(libs.accompanist.pager) // For HorizontalPager
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose) // Or the latest version

    implementation(libs.coil.compose)
    api(libs.icons.extended)

}