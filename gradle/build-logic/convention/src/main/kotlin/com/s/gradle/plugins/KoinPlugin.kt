package com.s.gradle.plugins

import com.s.gradle.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                // Core Koin for Android
                "implementation"(libs.findLibrary("koin.android").get())
                // Koin for Jetpack Compose
            }
        }
    }
}