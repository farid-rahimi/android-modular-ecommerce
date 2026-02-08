plugins {
    id("com.s.gradle.android.data")
}

android {
    namespace = "com.solutionium.data.woo.products"
}

dependencies {

    implementation(project(":data:api:woo"))
    implementation(project(":data:database"))

    api(libs.paging.runtime)

}