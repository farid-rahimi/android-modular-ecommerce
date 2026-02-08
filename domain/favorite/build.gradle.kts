plugins {
    id("com.s.gradle.android.domain")
}

android {
    namespace = "com.solutionium.domain.favorite"
}

dependencies {

    implementation(project(":data:favorite"))
    implementation(project(":data:woo-products"))
    implementation(project(":data:woo-user"))
    implementation(project(":domain:user"))

}