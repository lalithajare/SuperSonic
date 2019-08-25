/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.common.dependencyinjection

import android.content.ContentResolver
import com.supersonic.app.common.MyApplication
import com.supersonic.app.common.utilities.MusicTracksManager

class CompositionRoot {

    var mTrackManagerMusic: MusicTracksManager? = null

    public fun initializeTrackManager(): MusicTracksManager {
        if (mTrackManagerMusic == null) {
            mTrackManagerMusic = MusicTracksManager()
        }
        return mTrackManagerMusic!!

    }

}