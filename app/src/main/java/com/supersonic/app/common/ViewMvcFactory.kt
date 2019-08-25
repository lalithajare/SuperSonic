/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.supersonic.app.screens.tracklist.TrackListItemMvc
import com.supersonic.app.screens.tracklist.TrackListItemMvcImpl
import com.supersonic.app.tracks.screens.tracklist.TrackListMvc
import com.supersonic.app.tracks.screens.tracklist.TrackListMvcImpl

class ViewMvcFactory(private var mLayoutInflater: LayoutInflater) {

    public fun getTrackListMvc(parent: ViewGroup?): TrackListMvc {
        return TrackListMvcImpl(mLayoutInflater, parent, this)
    }

    public fun getTrackListItemMvc(parent: ViewGroup?): TrackListItemMvc {
        return TrackListItemMvcImpl(mLayoutInflater, parent)
    }

}