package com.chethan.buildsrc


object Dependencies {


    object Apps {
        const val compileSdk = 30
        const val minSdk = 23
        const val targetSdk = 30
        const val versionCode = 1
        const val versionName = "1.0.0"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.2"

    object AndroidX {
        private const val appcompactVersion = "1.3.0-alpha02"
        private const val coreKtxVersion = "1.5.0-alpha04"

        private const val nav_version = "2.3.1"

        const val appcompat = "androidx.appcompat:appcompat:$appcompactVersion"
        const val palette = "androidx.palette:palette:1.0.0"

        const val core = "androidx.core:core:$coreKtxVersion"
        const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"

        const val material = "com.google.android.material:material:1.2.1"
        const val flexbox = "com.google.android:flexbox:1.1.0"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.3"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
        const val cardView = "androidx.cardview:cardview:1.0.0"
        const val multidex = "androidx.multidex:multidex:2.0.0"

        object Navigation {
            const val navigationFragmentKTX =
                "androidx.navigation:navigation-fragment-ktx:$nav_version"
            const val navigationUIKTX = "androidx.navigation:navigation-ui-ktx:$nav_version"
            const val navigationDynamicKTX =
                "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"
            const val safeArg =
                "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"
        }

        object Compose {
            const val snapshot = ""
            const val version = "1.0.0-beta03"
            const val composePagingVersion = "1.0.0-alpha05"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val test = "androidx.compose.ui:ui-test:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiUtil = "androidx.compose.ui:ui-util:${version}"
            const val viewBinding = "androidx.compose.ui:ui-viewbinding:$version"
            const val pagingCompose = "androidx.paging:paging-compose:$composePagingVersion"
        }

        object Test {
            private const val version = "1.2.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2-rc01"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }

        object Room {
            private const val version = "2.3.0-alpha04"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

        object Lifecycle {
            private const val version = "2.2.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"

            const val viewModelCompose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha02"
        }

        object Fragment {
            private const val version = "1.3.0-beta01"
            const val fragment_ktx = "androidx.fragment:fragment-ktx:$version"
        }

        object Work {
            private const val version = "2.0.1"
            const val runtime = "androidx.work:work-runtime:$version"
            const val testing = "androidx.work:work-testing:$version"
            const val ktx = "androidx.work:work-runtime-ktx:$version"
        }
    }


    object Kotlin {
        private const val version = "1.4.31"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
        const val allopen = "org.jetbrains.kotlin:kotlin-allopen:$version"
    }

    object Coroutines {
        private const val version = "1.3.9"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"
        private const val gsonVersion = "2.8.8"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val gson = "com.squareup.retrofit2:converter-gson:$version"
        const val mock = "com.squareup.retrofit2:retrofit-mock:$version"
        const val SerializedAnnotation = "com.google.code.gson:gson:$gsonVersion"
    }

    object OkHttp {
        private const val version = "4.7.2"
        const val okhttp = "com.squareup.okhttp3:okhttp:$version"
        const val logging = "com.squareup.okhttp3:logging-interceptor:$version"
    }


    object HILT {
        private const val hiltVersion = "2.29.1-alpha"
        private const val hiltViewModel = "1.0.0-alpha02"

        const val android = "com.google.dagger:hilt-android:$hiltVersion"
        const val compiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        const val gradle_plugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"

        const val viewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$hiltViewModel"
        const val viewModelCompiler = "androidx.hilt:hilt-compiler:$hiltViewModel"
    }


    object LINT {
        private const val version = "0.39.0"
        const val ktlint = "com.pinterest:ktlint:$version"
    }

    object LOG {
        private const val version = "4.7.1"
        const val timber = "com.jakewharton.timber:timber:$version"

        const val chunk = "com.github.ChuckerTeam.Chucker:library:3.4.0"
        const val chunkNoOp = "com.github.ChuckerTeam.Chucker:library-no-op:3.4.0"
    }


    object Glide {
        private const val version = "4.11.0"
        const val runtime = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
        const val okhttp3 = "com.github.bumptech.glide:okhttp3-integration:$version"
        const val annotated = "com.github.bumptech.glide:annotations:4.9.0"
    }


}