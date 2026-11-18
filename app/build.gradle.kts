import java.util.Properties;

plugins {
    alias(libs.plugins.android.application)
}

val props = Properties().apply { // tempat menyimpan data yang diambil dari local.properties
    // proses mengambil data
    val f = rootProject.file("local.properties") // mencari local.properties di dir root project
    if (f.exists()) f.inputStream().use { // jika local.properties ada
        load(it)
    }
} // sama seperti function yang return value

android {
    namespace = "com.raihaan.bkksmkn6"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.raihaan.bkksmkn6"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String", "BKK_API_KEY",
            "\"${props.getProperty("BKK_API_KEY") ?: ""}\"" // mengambil variabel BKK_API_KEY dari local.properties
        )
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0")
    implementation("androidx.viewpager2:viewpager2:1.1.0:1.1.0")
    implementation("androidx.fragment:fragment-ktx:1.9.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.11.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.11.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.11.0")

    implementation("com.squareup.retrofit2:retrofit:2.11.0") // mengirimkan request ke server dengan memakai api
    implementation("com.squareup.retrofit2:converter-gson:2.11.0") // mengconvert json kedalam object
    implementation("io.coil-kt:coil:2.7.0") // untuk menampilkan gambar yang didapat dari response? idk
}