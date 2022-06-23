package com.example.shortcaserc.utils

import android.app.Application

class ShortcaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        lateinit var application: ShortcaseApplication
    }
}