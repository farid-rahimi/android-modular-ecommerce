package com.s.gradle.plugins


import com.s.gradle.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class FeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.s.gradle.android.library")
                apply("com.s.gradle.android.hilt")
            }

            dependencies {
                add("implementation", project(":data:model"))
                add("implementation", project(":core:ui:common"))
                add("implementation", project(":core:design-system"))

                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("lifecycle.runtime.compose").get())
                add("implementation", libs.findLibrary("lifecycle.viewmodel.compose").get())
            }
        }
    }
}
