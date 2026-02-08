plugins {
    id("com.s.gradle.android.domain")
}

android {
    namespace = "com.solutionium.domain.review"
}

dependencies {

    implementation(project(":data:woo-products"))

    api(libs.paging.runtime)

}