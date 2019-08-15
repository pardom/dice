package deps

val Oolong = dependency("org.oolong-kt", "oolong", "2.0.1")

object Android {
    val Material = dependency("com.google.android.material", "material", "1.1.0-alpha09")
    val RecyclerView = dependency("androidx.recyclerview", "recyclerview", "1.1.0-beta01")

    object Gradle : Group("com.android.tools.build", "3.4.2") {
        val Plugin = artifact("gradle")
    }
}

object Facebook {
    object Flipper : Group("com.facebook.flipper", "0.23.4") {
        val Debug = artifact("flipper")
        val Release = artifact("flipper-noop")
    }

    object Litho : Group("com.facebook.litho", "0.29.0") {
        val Core = artifact("litho-core")
        val Processor = artifact("litho-processor")
        val Widget = artifact("litho-widget")
    }

    val SoLoader = dependency("com.facebook.soloader", "soloader", "0.6.1")
}

object Kotlin : Group("org.jetbrains.kotlin", "1.3.41") {
    object Gradle {
        val Plugin = artifact("kotlin-gradle-plugin")
    }

    object Coroutines : Group("org.jetbrains.kotlinx", "1.3.0-RC") {
        val Android = artifact("kotlinx-coroutines-android")
    }

    object StdLib {
        val Common = artifact("kotlin-stdlib-common")
        val Jvm = artifact("kotlin-stdlib")
    }

    object Test {
        val AnnotationsCommon = artifact("kotlin-test-annotations-common")
        val Common = artifact("kotlin-test-common")
        val Jvm = artifact("kotlin-test")
        val JUnit5 = artifact("kotlin-test-junit5")
    }
}
