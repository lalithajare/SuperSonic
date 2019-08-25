/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.tracks.screens.tracklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.supersonic.app.models.MusicTrackDetails
import com.supersonic.app.common.ViewMvcFactory
import com.supersonic.app.screens.tracklist.TrackListItemMvc
import com.supersonic.app.screens.tracklist.TrackListItemMvcImpl

class MusicTrackAdapter(
    var listener: TrackListMvc.Listener,
    var viewMvcFactory: ViewMvcFactory
) : RecyclerView.Adapter<MusicTrackAdapter.MusicTrackViewHolder>(), TrackListItemMvc.Listener {

    var list: ArrayList<MusicTrackDetails>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicTrackViewHolder {
        val viewMvc = viewMvcFactory.getTrackListItemMvc(parent)
        viewMvc.registerListener(this)
        return MusicTrackViewHolder(viewMvc)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: MusicTrackViewHolder, position: Int) {
        holder.mViewMvc.bindMusicTrack(list!![position])
    }

    class MusicTrackViewHolder(var mViewMvc: TrackListItemMvc) :
        RecyclerView.ViewHolder(mViewMvc.getRootView())

    override fun onTrackClicked(musicTrackDetails: MusicTrackDetails) {
        listener.onTrackClickedListener(musicTrackDetails)
    }

}