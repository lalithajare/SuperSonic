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
import com.supersonic.app.tracks.screens.trackdetails.TrackDetailsActivity


class TrackListMvcImpl(inflater: LayoutInflater, viewGroup: ViewGroup?) : MusicTrackAdapter.MusicTrackClickedListener,
    TrackListMvc {

    private val mListeners = ArrayList<TrackListMvc.Listener>()

    private val mRootView = inflater.inflate(R.layout.layout_track_list, viewGroup, false)

    private lateinit var recyclerFiles: RecyclerView

    private lateinit var mMusicAdapter: MusicTrackAdapter


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
        return getRootView().findViewById<T>(id)
    }

    override fun setAdapter() {
        recyclerFiles.adapter = mMusicAdapter
        recyclerFiles.layoutManager = LinearLayoutManager(getContext())
        recyclerFiles.addItemDecoration(DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL))
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

    override fun onMusicTrackClicked(musicTrackDetails: MusicTrackDetails) {
        for (listener in mListeners) {
            listener.onTrackClickedListener(musicTrackDetails)
        }
    }


}