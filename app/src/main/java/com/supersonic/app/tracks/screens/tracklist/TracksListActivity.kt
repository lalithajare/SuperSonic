package com.supersonic.app.tracks.screens.tracklist

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.annotation.SuppressLint
import android.database.Cursor
import android.os.AsyncTask
import com.supersonic.app.common.BaseActivity
import com.supersonic.app.models.MusicTrackDetails
import com.supersonic.app.common.MyApplication
import com.supersonic.app.tracks.screens.trackdetails.TrackDetailsActivity


class TracksListActivity : BaseActivity(), TrackListMvc.Listener {

    private val REQ_READ_PERM = 994
    private var mTrackList = ArrayList<MusicTrackDetails>()

    private lateinit var mViewMvc: TrackListMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewMvc = TrackListMvcImpl(layoutInflater, null)

        mViewMvc.registerListener(this)

        setContentView(mViewMvc.getRootView())

        initActionbar("Tracks")

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



    private fun loadTracks() {

        val c = MyApplication.appInstance!!.trackManager!!.initAllTracks(mTrackList)

        mViewMvc.updateMusicList(mTrackList)

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
            mViewMvc.updateMusicList(mTrackList)
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

    override fun onTrackClickedListener(musicTrackDetails: MusicTrackDetails) {
        TrackDetailsActivity.beginWith(
            this,
            musicTrackDetails
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewMvc.unregisterListener(this)
    }

}
