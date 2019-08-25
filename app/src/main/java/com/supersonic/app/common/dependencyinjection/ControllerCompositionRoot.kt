/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.common.dependencyinjection

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.supersonic.app.common.ViewMvcFactory
import com.supersonic.app.common.utilities.MusicTracksManager

class ControllerCompositionRoot(
    private var compositionRoot: CompositionRoot,
    private var mActivity: AppCompatActivity
) {

    fun getMusicTracksManager(): MusicTracksManager {
        return compositionRoot.initializeTrackManager()
    }

    private fun getLayoutInflater(): LayoutInflater {
        return LayoutInflater.from(mActivity)
    }

    public fun getViewMvcFactory(): ViewMvcFactory {
        return ViewMvcFactory(getLayoutInflater())
    }

}