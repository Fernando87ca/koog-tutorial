import com.koog.example.buildlogic.readProperty
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    application
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

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    // como no vamos a crear un fat jar, vamos a tener que especificar a gradlew cuala es la main class
    mainClass.set("com.koog.example.FileEditorAgentKt")
}

buildConfig {
    packageName(group.toString())
    buildConfigField("openIAApiKey", readProperty("openIAApiKey"))
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(libs.bundles.koog)
    testImplementation(libs.kotlin.test)
}
