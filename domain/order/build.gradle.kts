plugins {
    id("com.s.gradle.android.domain")
}

android {
    namespace = "com.solutionium.domain.order"
}

dependencies {

    implementation(project(":data:woo-orders"))

    api(libs.paging.runtime)


}