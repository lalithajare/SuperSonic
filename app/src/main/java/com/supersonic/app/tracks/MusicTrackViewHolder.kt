package com.supersonic.app.tracks

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.supersonic.app.models.MusicTrackDetails
import com.supersonic.app.R
import com.supersonic.app.common.ViewHolder

class MusicTrackViewHolder(itemView: View, var mListener: View.OnClickListener) : ViewHolder(itemView) {

    private val imgThumb: ImageView = itemView.findViewById(R.id.img_thumb)
    private val txtMusicTitle: TextView = itemView.findViewById(R.id.txt_music_title)
    private val txtMusicAuthor: TextView = itemView.findViewById(R.id.txt_music_author)

    override fun onBindView(model: Any) {
        val musicFile = model as MusicTrackDetails

        if (musicFile.musicFileTitle != null && musicFile.musicFileTitle!!.isNotBlank()) {
            txtMusicTitle.text = musicFile.musicFileTitle
        }

        if (musicFile.musicFileArtist != null && musicFile.musicFileArtist!!.isNotBlank()) {
            txtMusicAuthor.text = musicFile.musicFileArtist
        }

        if (musicFile.musicFileThumb != null && musicFile.musicFileThumb!!.isNotBlank()) {
            Glide.with(itemView.context)
                .load(musicFile.musicFileThumb)
                .into(imgThumb)
        }else{
            imgThumb.setImageResource(android.R.drawable.ic_menu_crop)
        }

        itemView.tag = musicFile
        itemView.setOnClickListener(mListener)
    }

}