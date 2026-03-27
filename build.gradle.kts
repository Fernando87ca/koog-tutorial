import com.koog.example.buildlogic.readProperty
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.serialization)
    alias(libs.plugins.koog.build.logic)
}
group = "com.koog.example"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

buildConfig {
    packageName(group.toString())
    buildConfigField("openIAApiKey", readProperty("openIAApiKey"))
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(libs.koog.agent)
    testImplementation(libs.kotlin.test)
}
