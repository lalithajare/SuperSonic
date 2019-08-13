package com.supersonic.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MusicTrackAdapter(var list: ArrayList<MusicTrackDetails>) : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MusicTrackViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_music_file, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(list.get(position))
    }

}