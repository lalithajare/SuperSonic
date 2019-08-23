package com.supersonic.app.tracks.screens.tracklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.supersonic.app.models.MusicTrackDetails
import com.supersonic.app.R
import com.supersonic.app.common.ViewHolder

class MusicTrackAdapter(
    var listener: MusicTrackClickedListener
) : RecyclerView.Adapter<ViewHolder>() {

    var list: ArrayList<MusicTrackDetails>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MusicTrackViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_music_file, parent, false)
            , listener
        )
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(list?.get(position)!!)
    }

    public interface MusicTrackClickedListener {

        fun onMusicTrackClicked(musicTrackDetails: MusicTrackDetails)

    }

}