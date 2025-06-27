package com.md.satuwargaapp

import android.app.Application
import com.md.satuwargaapp.data.ApiClient

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ApiClient.init(this)
    }
}