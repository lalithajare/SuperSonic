 

/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.tracks.screens.tracklist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supersonic.app.R
import com.supersonic.app.models.MusicTrackDetails
import com.supersonic.app.screens.tracklist.TrackListItemMvc
import com.supersonic.app.screens.tracklist.TrackListItemMvcImpl
import com.supersonic.app.tracks.screens.trackdetails.TrackDetailsActivity


class TrackListMvcImpl(inflater: LayoutInflater, viewGroup: ViewGroup?) : TrackListItemMvc.Listener,
    TrackListMvc {

    private val mListeners = ArrayList<TrackListMvc.Listener>()

    private val mRootView = inflater.inflate(R.layout.layout_track_list, viewGroup, false)

    private lateinit var recyclerFiles: RecyclerView

    private var mMusicAdapter: MusicTrackAdapter


    init {
        initViews()
        mMusicAdapter = MusicTrackAdapter(this)
    }

    override fun getRootView(): View {
        return mRootView
    }

    override fun registerListener(listener: TrackListMvc.Listener) {
        mListeners.add(listener)
    }

    override fun unregisterListener(listener: TrackListMvc.Listener) {
        mListeners.remove(listener)
    }

    override fun getContext(): Context? {
        return getRootView().context
    }


    override fun initViews() {
        recyclerFiles = findViewById(R.id.recycler_files)
    }

    private fun <T : View> findViewById(id: Int): T {
        return getRootView().findViewById(id)
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
        if (mMusicAdapter.list == null) {
            mMusicAdapter.list = trackList
            setAdapter()
        } else {
            mMusicAdapter.list?.clear()
            mMusicAdapter.list?.addAll(trackList)
            mMusicAdapter.notifyDataSetChanged()
        }
    }

    override fun onTrackClicked(musicTrackDetails: MusicTrackDetails) {
        for (listener in mListeners) {
            listener.onTrackClickedListener(musicTrackDetails)
        }
    }

}