import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.movieapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.movieapp"
        minSdk = 26
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    // Compose
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0-alpha01")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation ("androidx.navigation:navigation-compose:2.5.3")
    implementation("io.coil-kt:coil-compose:2.3.0")

    // Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Accompanist
    implementation ("com.google.accompanist:accompanist-permissions:0.23.1")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // Retrofit with Kotlin serialization Converter
    implementation ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    // Room
    implementation ("androidx.room:room-runtime:2.5.1")

    implementation ("androidx.room:room-ktx:2.5.1")

    ksp ("androidx.room:room-compiler:2.5.1")

    // Kotlin serialization
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
}
