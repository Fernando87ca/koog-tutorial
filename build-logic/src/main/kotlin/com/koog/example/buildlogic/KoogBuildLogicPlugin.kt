package com.koog.example.buildlogic

import com.github.gmazzo.buildconfig.BuildConfigExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class KoogBuildLogicPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("com.github.gmazzo.buildconfig")

        target.extensions.configure(BuildConfigExtension::class.java) {
            useKotlinOutput {
                internalVisibility = false
            }
        }
    }
}
