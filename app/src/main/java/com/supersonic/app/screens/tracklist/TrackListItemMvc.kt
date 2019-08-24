 

/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.screens.tracklist

import android.view.View
import com.supersonic.app.models.MusicTrackDetails

interface TrackListItemMvc {

    interface Listener {
        fun onTrackClicked(musicTrackDetails: MusicTrackDetails)
    }

    fun getRootView():View
    fun registerListener(listener: Listener)
    fun unregisterListener(listener: Listener)
    fun bindMusicTrack(musicTrackDetails: MusicTrackDetails)

}