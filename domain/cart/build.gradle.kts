plugins {
    id("com.s.gradle.android.domain")
}

android {
    namespace = "com.solutionium.domain.cart"
}

dependencies {

    implementation(project(":data:cart"))
    implementation(project(":data:woo-products"))
}