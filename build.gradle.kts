buildscript {
    repositories {
        mavenCentral()
        jcenter()
        google()
    }
    dependencies {
        classpath(deps.Kotlin.Gradle.Plugin)
        classpath(deps.Android.Gradle.Plugin)
    }
}

subprojects {
    val GROUP: String by project
    val VERSION_NAME: String by project

    group = GROUP
    version = VERSION_NAME
}
