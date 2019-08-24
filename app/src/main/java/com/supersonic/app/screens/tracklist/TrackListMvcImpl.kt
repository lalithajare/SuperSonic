/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.tracks.screens.tracklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supersonic.app.R
import com.supersonic.app.common.BaseObservableViewMvc
import com.supersonic.app.models.MusicTrackDetails
import com.supersonic.app.screens.tracklist.TrackListItemMvc


class TrackListMvcImpl(inflater: LayoutInflater, viewGroup: ViewGroup?) : BaseObservableViewMvc<TrackListMvc.Listener>(),
    TrackListMvc
    , TrackListMvc.Listener {

    private val mListeners = ArrayList<TrackListMvc.Listener>()
    private lateinit var recyclerFiles: RecyclerView
    private lateinit var txtNoData: TextView
    private var mMusicAdapter: MusicTrackAdapter


    init {
        setRootView(inflater.inflate(R.layout.layout_track_list, viewGroup, false))
        initViews()
        mMusicAdapter = MusicTrackAdapter(this)

    }


    override fun initViews() {
        recyclerFiles = findViewById(R.id.recycler_files)
        txtNoData = findViewById(R.id.txt_no_data)
    }


    override fun setAdapter() {
        recyclerFiles.adapter = mMusicAdapter
        recyclerFiles.layoutManager = LinearLayoutManager(getContext())
        recyclerFiles.addItemDecoration(
            DividerItemDecoration(
                getContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }


    override fun updateMusicList(trackList: ArrayList<MusicTrackDetails>) {
        if (trackList.isNotEmpty()) {
            if (mMusicAdapter.list == null) {
                mMusicAdapter.list = trackList
                setAdapter()
            } else {
                mMusicAdapter.list?.clear()
                mMusicAdapter.list?.addAll(trackList)
                mMusicAdapter.notifyDataSetChanged()
            }
            txtNoData.visibility = View.GONE
        } else {
            txtNoData.visibility = View.VISIBLE
        }
    }

    override fun onTrackClickedListener(musicTrackDetails: MusicTrackDetails) {
        for (listener in mListeners) {
            listener.onTrackClickedListener(musicTrackDetails)
        }
    }

}