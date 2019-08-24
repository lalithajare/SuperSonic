/*
 * Created on 24/8/19 4:13 PM --> MM/dd/yyyy
 *
 * This code is generated from machine belonging to Lalit N. Hajare, Software Engineer III, Silicus Technologies,
 *  Aundh IT Park, Bopodi, Bhau Patil Marg, Pune - 411020.
 *
 *  Last modified on 24/8/19 4:13 PM --> MM/dd/yyyy
 *
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