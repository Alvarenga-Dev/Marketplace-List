plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("de.mannodermaus.android-junit5")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.alvarengadev.marketplacelist"

        minSdk = 24
        targetSdk = 33
        versionCode = 15
        versionName = "1.3.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder"
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    testOptions {
        animationsDisabled = true
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
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
        viewBinding = true
    }
    namespace = "com.alvarengadev.marketplacelist"
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.20")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    val version_lifecycle = "2.4.0"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$version_lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$version_lifecycle")

    val version_navigation = "2.5.3"
    implementation("androidx.navigation:navigation-fragment-ktx:$version_navigation")
    implementation("androidx.navigation:navigation-ui-ktx:$version_navigation")

    val version_hilt = "2.38.1"
    implementation("com.google.dagger:hilt-android:$version_hilt")
    kapt("com.google.dagger:hilt-compiler:$version_hilt")

    val version_room = "2.5.1"
    implementation("androidx.room:room-ktx:$version_room")
    kapt("androidx.room:room-compiler:$version_room")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:28.2.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    // For instrumentation tests
    val version_mockk = "1.12.0"
    androidTestImplementation("com.google.dagger:hilt-android-testing:$version_hilt")
    androidTestAnnotationProcessor("com.google.dagger:hilt-compiler:$version_hilt")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("com.google.truth:truth:1.0.1")

    androidTestImplementation("io.mockk:mockk-android:$version_mockk")

    val version_junit5 = "5.7.1"
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("org.junit.jupiter:junit-jupiter-api:$version_junit5")
    androidTestImplementation("de.mannodermaus.junit5:android-test-core:1.2.2")
    androidTestRuntimeOnly("de.mannodermaus.junit5:android-test-runner:1.2.2")

    // For local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:$version_hilt")
    testAnnotationProcessor("com.google.dagger:hilt-compiler:$version_hilt")

    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")
    testImplementation("com.google.truth:truth:1.0.1")
    testImplementation("junit:junit:4.13.2")

    testImplementation("io.mockk:mockk:$version_mockk")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$version_junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$version_junit5")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$version_junit5")
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("junit-jupiter")
        excludeEngines("junit-vintage")
    }
}
