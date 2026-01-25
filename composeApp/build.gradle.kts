import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.1.10"
    id("com.google.gms.google-services") version "4.4.2"
    alias(libs.plugins.ktlint)
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
        iosSimulatorArm64(),
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
            implementation(libs.google.play.services.ads)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)

            api(projects.kmpCore.coreLibrary)
            implementation(libs.decompose.coroutines)
            implementation(libs.firebase.database)
            implementation(libs.firebase.common)
            implementation(libs.platform.kore)
            implementation(libs.coil.compose)
            implementation(libs.decompose.mvi)
            implementation(libs.decompose.router)
            implementation(libs.coil.network.ktor3)
            implementation(coreLibs.kotlin.serialization)
            implementation(libs.paging.compose.common)
            implementation(libs.paging.common)
            implementation(libs.datastore.preferences)
            implementation(libs.datastore)

            implementation(libs.app.rating)
            implementation(libs.bundles.compotie)
            implementation(libs.basic.ads)
        }
    }

    targets.configureEach {
        compilations.configureEach {
            compilerOptions.configure {
                freeCompilerArgs.addAll("-Xexpect-actual-classes")
            }
        }
    }

    ktlint {
        verbose.set(true)
        outputToConsole.set(true)
        coloredOutput.set(true)
        ignoreFailures = true
        reporters {
            reporter(ReporterType.CHECKSTYLE)
            reporter(ReporterType.JSON)
            reporter(ReporterType.HTML)
        }
        filter {
            exclude("**/style-violations.kt")
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
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    signingConfigs {
        create("release") {
            storeFile = file("../wallyart.keystore")
            storePassword = "123456"
            keyAlias = "wallyart"
            keyPassword = "123456"
        }
    }

    defaultConfig {
        applicationId = "com.nmt.kmpwallpaper"
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.android.targetSdk
                .get()
                .toInt()
        versionCode = 4
        versionName = "1.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
