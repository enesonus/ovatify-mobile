plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    namespace = "com.sabanci.ovatify"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sabanci.ovatify"
        minSdk = 31
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {


    implementation("co.yml:ycharts:2.1.0")
    implementation("com.diogobernardino:williamchart:3.10.1")
    implementation("com.himanshoe:charty:2.0.0-alpha01")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.compose.ui:ui-graphics")
    implementation ("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.compose.material3:material3")
    /*
    implementation(project(":charty"))
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation ("androidx.activity:activity-compose:1.5.1")
    implementation (platform("androidx.compose:compose-bom:2022.10.00"))
    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.compose.ui:ui-graphics")
    implementation ("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.compose.material3:material3")
    androidTestImplementation (platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4")
    debugImplementation ("androidx.compose.ui:ui-tooling")
    debugImplementation ("androidx.compose.ui:ui-test-manifest")
     */

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.2.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // Use Gson as a converter
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.google.android.material:material:<version>")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("io.coil-kt:coil:2.5.0")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // Use Gson as a converter
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.google.android.material:material:<version>")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("io.coil-kt:coil:2.5.0")
    implementation("androidx.compose.ui:ui-graphics-android:1.5.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
}