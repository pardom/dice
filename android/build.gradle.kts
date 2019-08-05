plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

repositories {
    google()
    mavenCentral()
    jcenter()
}

android {
    compileSdkVersion(28)

    defaultConfig {
        applicationId = "dev.pardo.dice"
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation(project(":common"))
    implementation(deps.Android.Material)
    implementation(deps.Android.RecyclerView)
    implementation(deps.ItemAnimators)
    implementation(deps.Kotlin.StdLib.Jvm)
    implementation(deps.Kotlin.Coroutines.Android)
    implementation(deps.Square.Seismic)
}
