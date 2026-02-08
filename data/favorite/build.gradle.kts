plugins {
    id("com.s.gradle.android.data")
}

android {
    namespace = "com.solutionium.data.favorite"
}

dependencies {
    implementation(project(":data:api:woo"))
    implementation(project(":data:database"))
    implementation(project(":data:local"))

}