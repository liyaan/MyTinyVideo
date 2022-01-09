package com.liyaan.mytinyvideo

import android.app.Application
import com.liyaan.libcommon.utils.AppGlobals

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        AppGlobals.sApplication = this
    }
}