plugins {
    id("com.s.gradle.android.data")
}

android {
    namespace = "com.solutionium.data.woo.user"
}

dependencies {

    implementation(project(":data:api:woo"))
    implementation(project(":data:database"))
    implementation(project(":data:local"))

    api(libs.paging.runtime)

}