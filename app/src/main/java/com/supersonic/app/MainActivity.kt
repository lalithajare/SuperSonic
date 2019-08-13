package com.supersonic.app

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.util.jar.Manifest

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
                loadTracks(Environment.getExternalStorageDirectory())
            }
        } else {
            loadTracks(Environment.getExternalStorageDirectory())
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
    }

    private fun loadTracks(root: File) {
        for (file in root.listFiles()) {
            if (file.name.split(".")[1].equals("mp3"))
                mList.add(Utils.getMusicDetails(this, file))
        }
        mMusicAdapter.notifyDataSetChanged()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (permissions.isNotEmpty() && permissions[0] == android.Manifest.permission.READ_EXTERNAL_STORAGE
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            loadTracks(Environment.getExternalStorageDirectory())
        }
    }

}
