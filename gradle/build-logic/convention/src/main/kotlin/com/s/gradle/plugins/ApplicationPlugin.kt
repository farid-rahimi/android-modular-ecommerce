package com.s.gradle.plugins


import com.android.build.api.dsl.ApplicationExtension
import com.s.gradle.Versions
import com.s.gradle.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("com.s.gradle.android.application.compose")
                apply("com.s.gradle.android.koin")

            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid()

                defaultConfig {
                    targetSdk = Versions.TARGET_SDK
                }
            }

            dependencies {
                add("implementation", project(":core:design-system"))
            }
        }
    }
}
