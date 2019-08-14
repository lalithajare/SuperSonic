package com.supersonic.app

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.lang.RuntimeException
import java.util.jar.Manifest
import android.R.id
import android.content.CursorLoader
import android.widget.Toast
import android.widget.LinearLayout
import android.graphics.BitmapFactory
import android.graphics.Bitmap


class MainActivity : AppCompatActivity() {

    private val REQ_READ_PERM = 994

    private lateinit var recyclerFiles: RecyclerView

    private lateinit var mMusicAdapter: MusicTrackAdapter

    private var mList = ArrayList<MusicTrackDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initActionbar()

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

    private fun initActionbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.findViewById<TextView>(R.id.txt_title).text = "Tracks"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }

    private fun initViews() {
        recyclerFiles = findViewById(R.id.recycler_files)
    }

    private fun setAdapter() {
        mMusicAdapter = MusicTrackAdapter(mList)
        recyclerFiles.adapter = mMusicAdapter
        recyclerFiles.layoutManager = LinearLayoutManager(this)
        recyclerFiles.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    private fun loadTracks() {
        val c = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TRACK,

                MediaStore.Images.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,

                MediaStore.Images.Media.DATA,

                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.YEAR
            ),
            null,
            null,
            null
        )


        if (c != null) {
            while (c.moveToNext()) {
                val musicTrack = MusicTrackDetails(0L)
                musicTrack.musicFileAlbum = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                musicTrack.musicFileArtist = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                musicTrack.musicFileTrack = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TRACK))

//                musicTrack.musicFileThumb = getThumbnail(c.getInt(c.getColumnIndex(MediaStore.Images.Media._ID)))


                val albumId =
                    java.lang.Long.valueOf(c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)))
                val cursorAlbum = contentResolver.query(
                    MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART),
                    MediaStore.Audio.Albums._ID + "=" + albumId, null, null
                )

                if (cursorAlbum != null && cursorAlbum.moveToFirst()) {
                    val albumCoverPath =
                        cursorAlbum.getString(cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))
                    val data = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA))
                    musicTrack.musicFileThumb = albumCoverPath
                }

                musicTrack.musicFileTitle = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicTrack.musicFileDisplayName = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                musicTrack.musicFileUrl = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicTrack.musicFileDuration = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DURATION))
                musicTrack.musicFileYear = c.getString(c.getColumnIndex(MediaStore.Audio.Media.YEAR))
                mList.add(musicTrack)
            }
        } else {
            // ERROR
            throw RuntimeException("Error while getting information of Music File")
        }
        mMusicAdapter.notifyDataSetChanged()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (permissions.isNotEmpty() && permissions[0] == android.Manifest.permission.READ_EXTERNAL_STORAGE
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            loadTracks()
        }
    }

}
