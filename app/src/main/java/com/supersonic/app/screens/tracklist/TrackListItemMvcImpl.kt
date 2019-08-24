/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.screens.tracklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.supersonic.app.R
import com.supersonic.app.common.BaseObservableViewMvc
import com.supersonic.app.common.ObservableViewMvc
import com.supersonic.app.models.MusicTrackDetails
import com.supersonic.app.tracks.screens.tracklist.TrackListMvc

class TrackListItemMvcImpl(inflater: LayoutInflater, parent: ViewGroup) :

    BaseObservableViewMvc<TrackListItemMvc.Listener>()
    , TrackListItemMvc {


    var mMusicTrackDetails: MusicTrackDetails? = null

    private val imgThumb: ImageView
    private val txtMusicTitle: TextView
    private val txtMusicAuthor: TextView

    init {

        setRootView(inflater.inflate(R.layout.item_music_file, parent, false))

        imgThumb = findViewById(R.id.img_thumb)
        txtMusicTitle = findViewById(R.id.txt_music_title)
        txtMusicAuthor = findViewById(R.id.txt_music_author)

        getRootView().setOnClickListener {
            for (listener in getListeners()) {
                listener.onTrackClicked(mMusicTrackDetails!!)
            }
        }
    }

    override fun bindMusicTrack(musicTrackDetails: MusicTrackDetails) {

        mMusicTrackDetails = musicTrackDetails

        if (mMusicTrackDetails?.musicFileTitle != null && mMusicTrackDetails?.musicFileTitle!!.isNotBlank()) {
            txtMusicTitle.text = musicTrackDetails.musicFileTitle
        }

        if (mMusicTrackDetails?.musicFileArtist != null && mMusicTrackDetails?.musicFileArtist!!.isNotBlank()) {
            txtMusicAuthor.text = musicTrackDetails.musicFileArtist
        }

        if (mMusicTrackDetails?.musicFileThumb != null && mMusicTrackDetails?.musicFileThumb!!.isNotBlank()) {
            Glide.with(getRootView().context)
                .load(musicTrackDetails.musicFileThumb)
                .circleCrop()
                .into(imgThumb)
        } else {
            imgThumb.setImageResource(android.R.drawable.ic_menu_crop)
        }


    }

}