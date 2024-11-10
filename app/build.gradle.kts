plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.patricia.savvyhotels"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.patricia.savvyhotels"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // to be synced in th gradle scripts
    //    to help make http rest api request
    implementation("com.loopj.android:android-async-http:1.4.9")
    //    gson for converting json objects into an array
    implementation("com.google.code.gson:gson:2.8.7")
    //    for swipe reload/refresh in recycler view
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    //    for making profile images
    implementation("de.hdodenhof:circleimageview:3.0.1")

//    implementation ("com.github.mazrou:QuantityView:1.0.1")
}