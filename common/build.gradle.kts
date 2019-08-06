plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

repositories {
    mavenCentral()
    jcenter()
}

kotlin {
    jvm()
    iosX64()

    cocoapods {
        summary = "Dice"
        homepage = "https://github.com/pardom/dice"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(deps.Oolong)
                implementation(deps.Kotlin.StdLib.Common)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(deps.Kotlin.Test.AnnotationsCommon)
                implementation(deps.Kotlin.Test.Common)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(deps.Kotlin.Test.JUnit5)
                implementation(deps.Kotlin.Test.Jvm)
            }
        }
    }
}

// workaround for https://youtrack.jetbrains.com/issue/KT-27170
configurations.create("compileClasspath")
