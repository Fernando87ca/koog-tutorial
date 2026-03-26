plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.serialization)
}
group = "com.koog.example"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(23)
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(libs.bundles.koog)
    testImplementation(libs.kotlin.test)
}

