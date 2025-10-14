plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.21"
    id("com.google.gms.google-services")
}

android {
    namespace = "com.yoinerdev.quizzesia"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.yoinerdev.quizzesia"
        minSdk = 24
        targetSdk = 36
        versionCode = 2
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //hilt
    implementation("com.google.dagger:hilt-android:2.56.2")
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
    //navigation compose
    implementation(libs.androidx.navigation.compose)
    //serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    //    implementation("com.google.android.gms:play-services-ads:24.5.0")
    implementation("androidx.core:core-splashscreen:1.0.0")
    //material icons
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    // Coil
    implementation("io.coil-kt:coil-compose:2.7.0")
    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:3.0.0")

    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    //lucide icons
    implementation("com.composables:icons-lucide:1.1.0")

    implementation("androidx.datastore:datastore-preferences:1.1.7")

    //PDFBox-Android
    implementation("com.tom-roush:pdfbox-android:2.0.27.0")

    //lotti animations
    implementation("com.airbnb.android:lottie-compose:6.6.10")

    dependencies {
        implementation("io.socket:socket.io-client:2.1.2"){
            exclude(group = "org.json", module = "json")
        }
    }

    implementation("com.google.android.gms:play-services-ads:24.7.0")

    implementation("androidx.compose.animation:animation:1.9.3") // o la que tengas
    implementation("com.google.accompanist:accompanist-navigation-animation:0.36.0")


}