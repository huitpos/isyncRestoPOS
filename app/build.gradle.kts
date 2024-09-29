plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.isyncpos"
    compileSdk = 34

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt") // Path to your CMakeLists.txt
        }
    }

    defaultConfig {
        applicationId = "com.example.isyncpos"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] =
                    "$projectDir/schemas"
            }
        }
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_DEVELOPMENT_URL", "\"http://uat.isync.ph/\"")
            buildConfigField("String", "API_PRODUCTION_URL", "\"http://isync.ph/\"")
            buildConfigField("String", "SOCKET_DEVELOPMENT_URL", "\"http://uat-socket.isync.ph/\"")
            buildConfigField("String", "SOCKET_PRODUCTION_URL", "\"http://prod-socket.isync.ph/\"")
            buildConfigField("String", "IMAGE_URL", "\"https://isync-bucket.s3.ap-southeast-1.amazonaws.com/\"")
            buildConfigField("String", "ACTION_USB_PERMISSION", "\"com.android.example.USB_PERMISSION\"")
            buildConfigField("int", "REQUEST_CONNECTION_TIMEOUT", "120")
            buildConfigField("int", "REQUEST_READ_TIMEOUT", "120")
            buildConfigField("int", "REQUEST_WRITE_TIMEOUT", "120")
            buildConfigField("String", "POS_PROVIDER_NAME", "\"iSync Enterprise Inc.\"")
            buildConfigField("String", "POS_PROVIDER_ADDRESS", "\"B18 L39 Madrid St., Town and Country West Subd., Molino III Bacoor City Cavite 4102\"")
            buildConfigField("String", "POS_PROVIDER_TIN", "\"639-376-723-00000\"")
            buildConfigField("String", "POS_PROVIDER_ACCREDITATION", "\"XXX-XXXXXXXXX-XXXXXX-XXXXX\"")
            buildConfigField("String", "POS_PROVIDER_PTU", "\"XXXXXXXXXXX-XXX-XXXXX-XXX\"")
            buildConfigField("String", "POS_PROVIDER_DATE_ISSUED", "\"MM-DD-YYYY\"")
            buildConfigField("String", "CACHE_DEFAULT_VALUE", "\"DEFAULT_VALUE\"")
            buildConfigField("String", "APP_DATABASE", "\"pos\"")
            buildConfigField("String", "APP_VERSION", "\"1.2.2\"")
            buildConfigField("int", "APP_DEFAULT_LOADING_TIME", "2500")
            buildConfigField("int", "APP_DEFAULT_SYNC_TIME", "1500")
            buildConfigField("int", "APP_DEFAULT_SMALL_DEVICE_MIN", "300")
            buildConfigField("int", "APP_DEFAULT_SMALL_DEVICE_MAX", "800")
            buildConfigField("int", "APP_RESUME_TRANSACTION", "1")
            buildConfigField("int", "APP_BACKOUT", "2")
            buildConfigField("int", "APP_PAYOUT", "3")
            buildConfigField("int", "APP_CASH_FUND", "4")
            buildConfigField("int", "APP_STATEMENT_OF_ACCOUNT", "5")
            buildConfigField("int", "APP_OFFICIAL_RECEIPT", "6")
            buildConfigField("int", "APP_X_READING", "7")
            buildConfigField("int", "APP_Z_READING", "8")
            buildConfigField("int", "APP_PRODUCT_LIST", "9")
            buildConfigField("int", "APP_POST_VOID", "10")
            buildConfigField("int", "APP_ITEM_VOID", "11")
            buildConfigField("int", "APP_REPRINT", "12")
            buildConfigField("int", "APP_SAFEKEEPING", "13")
            buildConfigField("int", "APP_SPOT_AUDIT", "14")
            buildConfigField("int", "APP_TAKE_ORDER_RECEIPT", "15")
            buildConfigField("int", "APP_REPRINT_X_READING", "16")
            buildConfigField("int", "APP_REPRINT_Z_READING", "17")
            buildConfigField("int", "POS_OR_VALID_YEAR", "5")
            buildConfigField("int", "POS_CHECK_CACHE_LOGIN", "0")
            buildConfigField("int", "POS_ACCESS_POS", "652")
            buildConfigField("int", "POS_ACCESS_RESUME_TRANSACTION", "653")
            buildConfigField("int", "POS_ACCESS_RESUME_TRANSACTION_RESUME", "654")
            buildConfigField("int", "POS_ACCESS_BACKOUT", "655")
            buildConfigField("int", "POS_ACCESS_ORDERS", "656")
            buildConfigField("int", "POS_ACCESS_VIEW_RECEIPT", "657")
            buildConfigField("int", "POS_ACCESS_VIEW_RECEIPT_REPRINT", "658")
            buildConfigField("int", "POS_ACCESS_VIEW_RECEIPT_VOID", "659")
            buildConfigField("int", "POS_ACCESS_AR_REDEEMING", "660")
            buildConfigField("int", "POS_ACCESS_CUTOFF", "661")
            buildConfigField("int", "POS_ACCESS_CUTOFF_XREADING", "662")
            buildConfigField("int", "POS_ACCESS_CUTOFF_ZREADING", "663")
            buildConfigField("int", "POS_ACCESS_PAYOUT", "664")
            buildConfigField("int", "POS_ACCESS_SAFEKEEPING", "665")
            buildConfigField("int", "POS_ACCESS_SPOT_AUDIT", "666")
            buildConfigField("int", "POS_ACCESS_SYNC_DATA", "667")
            buildConfigField("int", "POS_ACCESS_UPLOAD_SERVER", "668")
            buildConfigField("int", "POS_ACCESS_BACKUP", "669")
            buildConfigField("int", "POS_ACCESS_SETTINGS", "670")
            buildConfigField("int", "POS_ACCESS_PAYMENTS", "671")
            buildConfigField("int", "POS_ACCESS_DISCOUNTS", "672")
            buildConfigField("int", "POS_ACCESS_ITEM_VOID", "673")
            buildConfigField("int", "POS_ACCESS_SOA", "674")
            buildConfigField("int", "POS_ACCESS_OPEN_DRAWER", "675")
            buildConfigField("int", "POS_ACCESS_ITEM_SELECT", "676")
            buildConfigField("int", "POS_ACCESS_ITEM_SELECT_UPDATE_PRICE", "677")
            buildConfigField("int", "POS_ACCESS_ITEM_SELECT_UPDATE_QTY", "678")
            buildConfigField("int", "POS_ACCESS_ITEM_SELECT_RETURN_ITEM", "679")
            buildConfigField("int", "POS_ACCESS_CLEAR_TRANSACTION", "680")
            buildConfigField("int", "POS_ACCESS_PAUSE_TRANSACTION", "681")
            buildConfigField("int", "POS_ACCESS_VIEW_PRODUCTS", "682")
            buildConfigField("int", "POS_ACCESS_SEARCH_PRODUCTS", "683")
            buildConfigField("int", "POS_ACCESS_SCAN_BARCODE", "684")
            buildConfigField("int", "POS_ACCESS_VIEW_DEPARTMENTS", "685")
            buildConfigField("int", "POS_API_TIMER_TRY_COUNTER", "5")
            buildConfigField("int", "APP_NAVIGATION_HAS_AR", "1")
            buildConfigField("String", "ENV", "\"DEVELOPMENT\"")
        }
        release {
            buildConfigField("String", "API_DEVELOPMENT_URL", "\"http://uat.isync.ph/\"")
            buildConfigField("String", "API_PRODUCTION_URL", "\"http://isync.ph/\"")
            buildConfigField("String", "SOCKET_DEVELOPMENT_URL", "\"http://uat-socket.isync.ph/\"")
            buildConfigField("String", "SOCKET_PRODUCTION_URL", "\"http://prod-socket.isync.ph/\"")
            buildConfigField("String", "IMAGE_URL", "\"https://isync-bucket.s3.ap-southeast-1.amazonaws.com/\"")
            buildConfigField("String", "ACTION_USB_PERMISSION", "\"com.android.example.USB_PERMISSION\"")
            buildConfigField("int", "REQUEST_CONNECTION_TIMEOUT", "120")
            buildConfigField("int", "REQUEST_READ_TIMEOUT", "120")
            buildConfigField("int", "REQUEST_WRITE_TIMEOUT", "120")
            buildConfigField("String", "POS_PROVIDER_NAME", "\"iSync Enterprise Inc.\"")
            buildConfigField("String", "POS_PROVIDER_ADDRESS", "\"B18 L39 Madrid St., Town and Country West Subd., Molino III Bacoor City Cavite 4102\"")
            buildConfigField("String", "POS_PROVIDER_TIN", "\"639-376-723-00000\"")
            buildConfigField("String", "POS_PROVIDER_ACCREDITATION", "\"XXX-XXXXXXXXX-XXXXXX-XXXXX\"")
            buildConfigField("String", "POS_PROVIDER_PTU", "\"XXXXXXXXXXX-XXX-XXXXX-XXX\"")
            buildConfigField("String", "POS_PROVIDER_DATE_ISSUED", "\"MM-DD-YYYY\"")
            buildConfigField("String", "CACHE_DEFAULT_VALUE", "\"DEFAULT_VALUE\"")
            buildConfigField("String", "APP_DATABASE", "\"pos\"")
            buildConfigField("String", "APP_VERSION", "\"1.2.2\"")
            buildConfigField("int", "APP_DEFAULT_LOADING_TIME", "2500")
            buildConfigField("int", "APP_DEFAULT_SYNC_TIME", "1500")
            buildConfigField("int", "APP_DEFAULT_SMALL_DEVICE_MIN", "300")
            buildConfigField("int", "APP_DEFAULT_SMALL_DEVICE_MAX", "800")
            buildConfigField("int", "APP_RESUME_TRANSACTION", "1")
            buildConfigField("int", "APP_BACKOUT", "2")
            buildConfigField("int", "APP_PAYOUT", "3")
            buildConfigField("int", "APP_CASH_FUND", "4")
            buildConfigField("int", "APP_STATEMENT_OF_ACCOUNT", "5")
            buildConfigField("int", "APP_OFFICIAL_RECEIPT", "6")
            buildConfigField("int", "APP_X_READING", "7")
            buildConfigField("int", "APP_Z_READING", "8")
            buildConfigField("int", "APP_PRODUCT_LIST", "9")
            buildConfigField("int", "APP_POST_VOID", "10")
            buildConfigField("int", "APP_ITEM_VOID", "11")
            buildConfigField("int", "APP_REPRINT", "12")
            buildConfigField("int", "APP_SAFEKEEPING", "13")
            buildConfigField("int", "APP_SPOT_AUDIT", "14")
            buildConfigField("int", "APP_TAKE_ORDER_RECEIPT", "15")
            buildConfigField("int", "APP_REPRINT_X_READING", "16")
            buildConfigField("int", "APP_REPRINT_Z_READING", "17")
            buildConfigField("int", "POS_OR_VALID_YEAR", "5")
            buildConfigField("int", "POS_CHECK_CACHE_LOGIN", "0")
            buildConfigField("int", "POS_ACCESS_POS", "652")
            buildConfigField("int", "POS_ACCESS_RESUME_TRANSACTION", "653")
            buildConfigField("int", "POS_ACCESS_RESUME_TRANSACTION_RESUME", "654")
            buildConfigField("int", "POS_ACCESS_BACKOUT", "655")
            buildConfigField("int", "POS_ACCESS_ORDERS", "656")
            buildConfigField("int", "POS_ACCESS_VIEW_RECEIPT", "657")
            buildConfigField("int", "POS_ACCESS_VIEW_RECEIPT_REPRINT", "658")
            buildConfigField("int", "POS_ACCESS_VIEW_RECEIPT_VOID", "659")
            buildConfigField("int", "POS_ACCESS_AR_REDEEMING", "660")
            buildConfigField("int", "POS_ACCESS_CUTOFF", "661")
            buildConfigField("int", "POS_ACCESS_CUTOFF_XREADING", "662")
            buildConfigField("int", "POS_ACCESS_CUTOFF_ZREADING", "663")
            buildConfigField("int", "POS_ACCESS_PAYOUT", "664")
            buildConfigField("int", "POS_ACCESS_SAFEKEEPING", "665")
            buildConfigField("int", "POS_ACCESS_SPOT_AUDIT", "666")
            buildConfigField("int", "POS_ACCESS_SYNC_DATA", "667")
            buildConfigField("int", "POS_ACCESS_UPLOAD_SERVER", "668")
            buildConfigField("int", "POS_ACCESS_BACKUP", "669")
            buildConfigField("int", "POS_ACCESS_SETTINGS", "670")
            buildConfigField("int", "POS_ACCESS_PAYMENTS", "671")
            buildConfigField("int", "POS_ACCESS_DISCOUNTS", "672")
            buildConfigField("int", "POS_ACCESS_ITEM_VOID", "673")
            buildConfigField("int", "POS_ACCESS_SOA", "674")
            buildConfigField("int", "POS_ACCESS_OPEN_DRAWER", "675")
            buildConfigField("int", "POS_ACCESS_ITEM_SELECT", "676")
            buildConfigField("int", "POS_ACCESS_ITEM_SELECT_UPDATE_PRICE", "677")
            buildConfigField("int", "POS_ACCESS_ITEM_SELECT_UPDATE_QTY", "678")
            buildConfigField("int", "POS_ACCESS_ITEM_SELECT_RETURN_ITEM", "679")
            buildConfigField("int", "POS_ACCESS_CLEAR_TRANSACTION", "680")
            buildConfigField("int", "POS_ACCESS_PAUSE_TRANSACTION", "681")
            buildConfigField("int", "POS_ACCESS_VIEW_PRODUCTS", "682")
            buildConfigField("int", "POS_ACCESS_SEARCH_PRODUCTS", "683")
            buildConfigField("int", "POS_ACCESS_SCAN_BARCODE", "684")
            buildConfigField("int", "POS_ACCESS_VIEW_DEPARTMENTS", "685")
            buildConfigField("int", "POS_API_TIMER_TRY_COUNTER", "5")
            buildConfigField("int", "APP_NAVIGATION_HAS_AR", "1")
            buildConfigField("String", "ENV", "\"PRODUCTION\"")
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
}

dependencies {
    val roomVersion = "2.6.1"
    val retroFitVersion = "2.11.0"
    val picassoVersion = "2.71828"
    val roomBackup = "1.0.1"
    val escPOSVersion = "3.3.0"
    val okHTTPVersion = "4.12.0"
    val bcryptVersion = "0.10.2"
    val socketIOVersion = "2.1.1";
//    val playServiceVersion = "20.1.3"

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation("com.squareup.retrofit2:converter-gson:$retroFitVersion")
    implementation("com.squareup.retrofit2:retrofit:$retroFitVersion")
    implementation("com.squareup.picasso:picasso:$picassoVersion")
    implementation("de.raphaelebner:roomdatabasebackup:$roomBackup")
    implementation("com.github.DantSu:ESCPOS-ThermalPrinter-Android:$escPOSVersion")
    implementation("com.squareup.okhttp3:okhttp:$okHTTPVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHTTPVersion")
    implementation(group = "at.favre.lib", name = "bcrypt", version = "$bcryptVersion")
    implementation("androidx.activity:activity-ktx:1.9.2")
    implementation("androidx.fragment:fragment-ktx:1.8.3")
    implementation("io.socket:socket.io-client:$socketIOVersion")
//    implementation("com.google.android.gms:play-services-vision:$playServiceVersion")

}