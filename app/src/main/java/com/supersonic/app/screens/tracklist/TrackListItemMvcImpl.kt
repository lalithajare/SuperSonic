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
import com.supersonic.app.models.MusicTrackDetails

class TrackListItemMvcImpl(inflater: LayoutInflater, parent: ViewGroup) : TrackListItemMvc {


    var mRootView: View = inflater.inflate(R.layout.item_music_file, parent, false)
    var mMusicTrackDetails: MusicTrackDetails? = null

    private val imgThumb: ImageView
    private val txtMusicTitle: TextView
    private val txtMusicAuthor: TextView

    private val listeners = ArrayList<TrackListItemMvc.Listener>()

    init {

        imgThumb = mRootView.findViewById(R.id.img_thumb)
        txtMusicTitle = mRootView.findViewById(R.id.txt_music_title)
        txtMusicAuthor = mRootView.findViewById(R.id.txt_music_author)

        mRootView.setOnClickListener {
            for (listener in listeners) {
                listener.onTrackClicked(mMusicTrackDetails!!)
            }
        }
    }

    override fun getRootView(): View {
        return mRootView
    }

    override fun registerListener(listener: TrackListItemMvc.Listener) {
        listeners.add(listener)
    }

    override fun unregisterListener(listener: TrackListItemMvc.Listener) {
        listeners.remove(listener)
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
            Glide.with(mRootView.context)
                .load(musicTrackDetails.musicFileThumb)
                .into(imgThumb)
        } else {
            imgThumb.setImageResource(android.R.drawable.ic_menu_crop)
        }


    }

}