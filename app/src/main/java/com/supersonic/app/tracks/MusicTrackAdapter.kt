package com.supersonic.app.tracks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.supersonic.app.models.MusicTrackDetails
import com.supersonic.app.R
import com.supersonic.app.common.ViewHolder

class MusicTrackAdapter(
        var list: ArrayList<MusicTrackDetails>,
        var listener: View.OnClickListener
) : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MusicTrackViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_music_file, parent, false)
                , listener
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(list.get(position))
    }

}