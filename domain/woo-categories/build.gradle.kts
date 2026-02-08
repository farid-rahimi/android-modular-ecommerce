plugins {
    id("com.s.gradle.android.domain")
}

android {
    namespace = "com.solutionium.domain.woo.categories"
}

dependencies {

    implementation(project(":data:woo-categories"))

}