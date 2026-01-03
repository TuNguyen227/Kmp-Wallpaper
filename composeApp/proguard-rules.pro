##  ________________________________________________________________________________________________________________

-dontwarn org.**
-dontwarn com.**
-dontwarn java.**
-dontwarn javax.**
-dontwarn sun.**



##  ________________________________________________________________________________________________________________

-keep class android.** { *; }
-keep class org.** { *; }
-keep class java.** { *; }
-keep class javax.** { *; }
-keep class sun.** { *; }
-keep class de.mindpipe.** { *; }
-keep class com.j256.** { *; }


##  ________________________________________________________________________________________________________________
## Preserve line numbers in the obfuscated stack traces.
-keepattributes LineNumberTable
-keepattributes SourceFile