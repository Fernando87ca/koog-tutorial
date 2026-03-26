plugins {
    alias(libs.plugins.kotlin.jvm)
}
group = "com.koog.example"
version = "1.0-SNAPSHOT"
dependencies {
    testImplementation(libs.kotlin.test)
}
kotlin {
    jvmToolchain(23)
}
tasks.test {
    useJUnitPlatform()
}
