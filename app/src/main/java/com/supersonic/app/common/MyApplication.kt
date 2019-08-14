package com.supersonic.app.common

import android.app.Application
import com.supersonic.app.common.utilities.TracksManager

class MyApplication : Application() {

    companion object {
        var appInstance: MyApplication? = null
    }

    var trackManager: TracksManager? = null

    override fun onCreate() {
        super.onCreate()
        trackManager = TracksManager(contentResolver)
        appInstance = this
    }

}