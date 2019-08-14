package com.supersonic.app.tracks.screens

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.annotation.SuppressLint
import android.database.Cursor
import android.os.AsyncTask
import android.view.View
import com.supersonic.app.common.BaseActivity
import com.supersonic.app.models.MusicTrackDetails
import com.supersonic.app.R
import com.supersonic.app.common.MyApplication
import com.supersonic.app.common.utilities.TracksManager
import com.supersonic.app.tracks.MusicTrackAdapter


class TracksActivity : BaseActivity(), View.OnClickListener {

    private val REQ_READ_PERM = 994

    private lateinit var recyclerFiles: RecyclerView

    private lateinit var mMusicAdapter: MusicTrackAdapter

    private var mTrackList = ArrayList<MusicTrackDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initActionbar(getString(R.string.tracks))

        initViews()

        setAdapter()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQ_READ_PERM)
            } else {
                loadTracks()
            }
        } else {
            loadTracks()
        }

    }


    private fun initViews() {
        recyclerFiles = findViewById(R.id.recycler_files)
    }

    private fun setAdapter() {
        mMusicAdapter = MusicTrackAdapter(mTrackList, this)
        recyclerFiles.adapter = mMusicAdapter
        recyclerFiles.layoutManager = LinearLayoutManager(this)
        recyclerFiles.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    private fun loadTracks() {

        val c = MyApplication.appInstance!!.trackManager!!.initAllTracks(mTrackList)

        mMusicAdapter.notifyDataSetChanged()

        loadImageTask.execute(c)

    }

    private val loadImageTask = @SuppressLint("StaticFieldLeak")
    object : AsyncTask<Cursor, Void, Void>() {

        private var cursor: Cursor? = null

        override fun doInBackground(vararg params: Cursor): Void? {
            cursor = params[0]
            loadImagesForTracks(cursor!!)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            mMusicAdapter.notifyDataSetChanged()
        }
    }

    private fun loadImagesForTracks(c: Cursor) {
        for (musicTrack in mTrackList) {
            val cursorAlbum = contentResolver.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART),
                MediaStore.Audio.Albums._ID + "=" + musicTrack.musicFileAlbumId, null, null
            )

            if (cursorAlbum != null && cursorAlbum.moveToFirst()) {
                val albumCoverPath =
                    cursorAlbum.getString(cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))
                musicTrack.musicFileThumb = albumCoverPath
            }
            cursorAlbum?.close()
        }
        c.close()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (permissions.isNotEmpty() && permissions[0] == android.Manifest.permission.READ_EXTERNAL_STORAGE
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            loadTracks()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.item_music_track -> {
                if (v.tag != null && v.tag is MusicTrackDetails) {
                    val musicTrackDetails = v.tag as MusicTrackDetails
                    TrackDetailsActivity.beginWith(this, musicTrackDetails)
                }
            }
        }
    }


}
