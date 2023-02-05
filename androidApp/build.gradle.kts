plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
}

android {
    namespace = "pl.dszerszen.multidrink.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "pl.dszerszen.multidrink.android"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.3.3")
    implementation("androidx.compose.ui:ui-tooling:1.3.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.3")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation ("androidx.activity:activity-compose:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("androidx.navigation:navigation-compose:2.5.3")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation ("io.insert-koin:koin-core:3.2.2")
    implementation ("io.insert-koin:koin-android:3.3.0")
    implementation ("io.insert-koin:koin-androidx-compose:3.3.0")

    implementation("io.coil-kt:coil-compose:2.2.2")

    //Test
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation ("io.mockk:mockk:1.13.4")
    testImplementation ("io.mockk:mockk-agent:1.13.4")
    testImplementation ("io.mockk:mockk-android:1.13.4")
    testImplementation ("io.kotest:kotest-assertions-core:5.5.4")
}