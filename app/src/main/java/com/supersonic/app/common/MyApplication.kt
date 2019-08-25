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
import com.supersonic.app.common.dependencyinjection.CompositionRoot
import com.supersonic.app.common.utilities.MusicTracksManager

class MyApplication : Application() {

    companion object {
        var appInstance: MyApplication? = null
    }


    private var mCompositionRoot: CompositionRoot? = null

    override fun onCreate() {
        super.onCreate()
        mCompositionRoot = CompositionRoot()
        appInstance = this
    }

    public fun getCompositionRoot(): CompositionRoot {
        return mCompositionRoot!!
    }

}