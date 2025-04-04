import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import kotlin.math.exp

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.1.10"
    id("com.google.gms.google-services") version "4.4.2"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            export(projects.kmpCore.coreLibrary)
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.common.ktx)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)

            api(projects.kmpCore.coreLibrary)
            implementation(coreLibs.navigation.decompose)
            implementation(coreLibs.decompose.extension)
            implementation(libs.decompose.coroutines)
            implementation(libs.landscapist.coil3)
            implementation(libs.firebase.database)
            implementation(libs.firebase.common)
            implementation(libs.platform.kore)
            implementation(libs.coil.compose)
            implementation(libs.decompose.mvi)
        }
    }

    targets.configureEach {
        compilations.configureEach {
            compilerOptions.configure {
                freeCompilerArgs.addAll("-Xexpect-actual-classes")
            }
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.nmt.kmpwallpaper.composeApp.commonMain"
    generateResClass = always
}

android {
    namespace = "com.nmt.kmpwallpaper"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.nmt.kmpwallpaper"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

