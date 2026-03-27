plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.buildconfig.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("koogBuildLogic") {
            id = "com.koog.example.build-logic"
            implementationClass = "com.koog.example.buildlogic.KoogBuildLogicPlugin"
        }
    }
}
