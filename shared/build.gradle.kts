plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.7.21"
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
                  //coroutines
                  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                  //datetime
                  implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                  //ktor
                  implementation("io.ktor:ktor-client-core:$ktorVersion")
                  implementation("io.ktor:ktor-client-logging:$ktorVersion")
                  implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                  implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                  //serialization
                  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
                  implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.3")
                  //sql
                  implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
                  //koin
                  implementation("io.insert-koin:koin-core:3.2.0")


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
                //ktor
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                //sql
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
                //koin
                implementation("io.insert-koin:koin-android:3.3.0")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                //ktor
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                //sql
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
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
    database("DrinksDatabase") {
        packageName = "pl.dszerszen.multidrink.db"
        sourceFolders = listOf("sqldelight")
    }
}