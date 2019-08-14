package com.supersonic.app

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class MusicTrackViewHolder(itemView: View) : ViewHolder(itemView) {

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
        }
    }

}