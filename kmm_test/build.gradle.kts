plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("org.jetbrains.kotlinx.kover")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    multiplatformSwiftPackage {
        packageName("speakingMl")
        swiftToolsVersion("5.9")
        targetPlatforms {
            iOS { v("14") }
        }
        outputDirectory(File(rootDir, "/swiftpackage"))
    }

    listOf(
        iosX64(),
    ).forEach {
        it.binaries.framework {
            baseName = "speakingMl"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
                implementation("io.insert-koin:koin-core:3.4.0")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
                implementation("com.russhwolf:multiplatform-settings:1.0.0")
                implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0")
                implementation("io.ktor:ktor-client-core:2.3.0")
                implementation("io.ktor:ktor-client-logging:2.3.0")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
                implementation("io.ktor:ktor-client-auth:2.3.0")

                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")

                implementation("co.touchlab:kermit:1.2.2")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

                implementation("com.squareup.okio:okio:3.6.0")
                implementation("com.github.kittinunf.result:result:5.5.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("io.insert-koin:koin-android:3.4.0")
                implementation("app.cash.sqldelight:android-driver:2.0.0-alpha05")
                implementation("io.ktor:ktor-client-okhttp:2.3.0")
            }
        }
        val iosX64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            dependencies {
                implementation("app.cash.sqldelight:native-driver:2.0.0-alpha05")
                implementation("io.ktor:ktor-client-darwin:2.3.0")
            }
        }
    }
}

android {
    namespace = "com.np.kmm_test"
    compileSdk = 33
    defaultConfig {
        minSdk = 29
    }
}
