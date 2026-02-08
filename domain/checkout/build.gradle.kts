plugins {
    id("com.s.gradle.android.domain")
}

android {
    namespace = "com.solutionium.domain.checkout"
}

dependencies {

    implementation(project(":data:woo-checkout"))
    implementation(project(":data:woo-orders"))

}