package com.supersonic.app.tracks.screens.tracklist

import android.os.Bundle
import android.provider.MediaStore
import android.annotation.SuppressLint
import android.database.Cursor
import android.os.AsyncTask
import com.supersonic.app.common.BaseActivity
import com.supersonic.app.models.MusicTrackDetails
import com.supersonic.app.common.MyApplication
import com.supersonic.app.common.PermissionManager
import com.supersonic.app.tracks.screens.trackdetails.TrackDetailsActivity


class TracksListActivity : BaseActivity(), TrackListMvc.Listener, PermissionManager.PermissionResponseCallback {

    private val REQ_READ_PERM = 994

    private var mTrackList = ArrayList<MusicTrackDetails>()

    private lateinit var mPermissionManager: PermissionManager

    private lateinit var mViewMvc: TrackListMvc


    /**
     * PERMISSIONS necessary on this screen
     */
    private val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPermissionManager = PermissionManager(this, this)

        mViewMvc = TrackListMvcImpl(layoutInflater, null)

        mViewMvc.registerListener(this)

        setContentView(mViewMvc.getRootView())

        initActionbar("Tracks")

        mPermissionManager.checkPermissions(permissions)

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
        mPermissionManager.rectifyPermissionResult(grantResults, permissions)
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

    override fun onPermissionGranted() {
        loadTracks()
    }

    override fun onPermissionDenied() {

    }


}
