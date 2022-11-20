plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

kotlin {
    android()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val ktorVersion = "2.1.3"
        val sqlDelightVersion = "1.5.3"

        val commonMain by getting {
              dependencies {
                  implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
                  implementation("io.ktor:ktor-client-core:$ktorVersion")
                  implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
              }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
             }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "pl.dszerszen.multidrink"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
        targetSdk = 33
    }
}

sqldelight {
    database("LocalDb") {
        packageName = "multidrink.db"
        sourceFolders = listOf("sqldelight")
    }
}