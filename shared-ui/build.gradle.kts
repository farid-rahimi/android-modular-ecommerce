plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    val frameworkBaseName = "sharedUIKit"

    androidLibrary {
        namespace = "com.solutionium.sharedui"
        compileSdk = 36
        minSdk = 24
    }

    iosArm64 {
        binaries.framework {
            baseName = frameworkBaseName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = frameworkBaseName
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
