rootProject.name = "kmp_wallpaper"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        versionCatalogs {
            create("coreLibs") {
                from(files("Kmp-Core/gradle/corelibs.versions.toml")) // Load submodule-specific versions
            }
        }
    }
}

include(":composeApp")
include(":Kmp-Core")
include(":Kmp-Core:coreLibrary")