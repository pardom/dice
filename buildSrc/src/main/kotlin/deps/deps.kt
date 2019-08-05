package deps

val ItemAnimators = dependency("com.mikepenz", "itemanimators", "1.1.0")

object Android {
    val Material = dependency("com.google.android.material", "material", "1.1.0-alpha09")
    val RecyclerView = dependency("androidx.recyclerview", "recyclerview", "1.1.0-beta01")

    object Gradle : Group("com.android.tools.build", "3.4.2") {
        val Plugin = artifact("gradle")
    }
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

object Oolong : Group("org.oolong-kt", "2.0.1") {
    val Core = artifact("oolong")
}

object Square {
    val Seismic = dependency("com.squareup", "seismic", "1.0.2")
}


