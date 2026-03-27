package com.koog.example.buildlogic

import org.gradle.api.GradleException
import org.gradle.api.Project

fun Project.readProperty(name: String): String =
    providers.gradleProperty(name)
        .orElse(providers.environmentVariable(name))
        .orNull
        ?: throw GradleException("Missing property '$name'. Define it in gradle.properties or as an environment variable.")
