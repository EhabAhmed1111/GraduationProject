import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    id("kotlin-parcelize")
    alias(libs.plugins.ktfmt)
    alias(libs.plugins.detekt)
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.kapt)
    id("com.google.devtools.ksp")
    alias(libs.plugins.compose.compiler)

}

android {
    namespace = "com.example.student_project"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.student_project"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ktfmt {
    kotlinLangStyle()
}
tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
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

    implementation(libs.androidx.window)
    implementation(libs.androidx.window.v110)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

     //viewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //material
    implementation ("com.google.android.material:material:1.13.0-alpha13")

    implementation(libs.androidx.appcompat)

    //life cycle
    implementation(libs.androidx.lifecycle.runtime.compose)

    //navigation
    implementation("androidx.navigation:navigation-compose:2.8.6")

    //coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.9.0")

    //Hilt-Dagger
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")


    // For file operations
    implementation("commons-io:commons-io:2.11.0")

    //for activity launcher result
    implementation("androidx.activity:activity-compose:1.8.1")

    //room
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    //viewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    //coil
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("com.google.accompanist:accompanist-coil:0.10.0")

    //concurrent
    implementation("androidx.concurrent:concurrent-futures:1.2.0")

    // Kotlin
    implementation("androidx.concurrent:concurrent-futures-ktx:1.2.0")

    //logging interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //phoneNumberPicker
//    implementation ("com.github.slaviboy:ComposeCountryPicker:1.0.0")
//    implementation ("com.googlecode.libphonenumber:libphonenumber:8.13.5")
//    implementation ("com.hbb20:ccp:2.7.2")
//    implementation ("com.github.murgupluoglu:flagkit-android:1.0.3")

    //exo-player
    implementation ("androidx.media3:media3-exoplayer:1.5.1")
    implementation ("androidx.media3:media3-ui:1.5.1")
    implementation ("androidx.media3:media3-common:1.5.1")
}

