package com.s.gradle.plugins


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class DomainPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.s.gradle.android.library")
                apply("com.s.gradle.android.koin")

            }

            dependencies {
                add("implementation", project(":data:model"))
            }
        }
    }
}
