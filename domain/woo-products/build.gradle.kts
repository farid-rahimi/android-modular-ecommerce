plugins {
    id("com.s.gradle.android.domain")
}

android {
    namespace = "com.solutionium.domain.woo.products"
}

dependencies {

    implementation(project(":data:woo-products"))

    api(libs.paging.runtime)

}