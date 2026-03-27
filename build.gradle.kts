import org.gradle.api.GradleException
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.serialization)
    alias(libs.plugins.buildconfig)
}
group = "com.koog.example"
version = "1.0-SNAPSHOT"

fun Project.readProperty(name: String): String =
    providers.gradleProperty(name)
        .orElse(providers.environmentVariable(name))
        .orNull
        ?: throw GradleException("Missing secret '$name'. Define it in gradle.properties or as an environment variable.")

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

buildConfig {
    packageName(group.toString())
    useKotlinOutput {
        internalVisibility = false
    }
    buildConfigField("googleApiKey", readProperty("googleApiKey"))
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(libs.koog.agent)
    testImplementation(libs.kotlin.test)
}
