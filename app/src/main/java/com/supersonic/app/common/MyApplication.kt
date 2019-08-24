

/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

/*
 *
 */

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