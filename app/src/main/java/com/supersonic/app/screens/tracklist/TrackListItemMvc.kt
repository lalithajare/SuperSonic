/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.screens.tracklist

import android.view.View
import com.supersonic.app.common.ObservableViewMvc
import com.supersonic.app.common.ViewMvc
import com.supersonic.app.models.MusicTrackDetails

interface TrackListItemMvc : ObservableViewMvc<TrackListItemMvc.Listener> {

    interface Listener {
        fun onTrackClicked(musicTrackDetails: MusicTrackDetails)
    }

    fun bindMusicTrack(musicTrackDetails: MusicTrackDetails)

}