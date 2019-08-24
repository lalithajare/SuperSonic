/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.tracks.screens.tracklist

import android.content.Context
import android.view.View
import com.supersonic.app.models.MusicTrackDetails

interface TrackListMvc {

    interface Listener {

        fun onTrackClickedListener(musicTrackDetails: MusicTrackDetails)

    }

    fun getRootView(): View
    fun registerListener(listener: TrackListMvc.Listener)
    fun unregisterListener(listener: TrackListMvc.Listener)
    fun getContext(): Context?
    fun initViews()
    fun setAdapter()
    fun updateMusicList(trackList: ArrayList<MusicTrackDetails>)
}