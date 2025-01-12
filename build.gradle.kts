// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.0.2")
//        classpath 'org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21'
//        classpath "androidx.navigation:navigation-safe-args-gradle-plugin:2.7.6"
////        classpath 'com.google.dagger:hilt-android-gradle-plugin:2.42'
//        classpath 'com.google.dagger:hilt-android-gradle-plugin:2.50'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false

}