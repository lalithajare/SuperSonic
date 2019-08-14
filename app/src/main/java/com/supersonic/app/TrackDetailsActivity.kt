package com.supersonic.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import com.bumptech.glide.Glide

class TrackDetailsActivity : BaseActivity() {

    companion object ActivtyStarter {

        fun beginWith(activity: BaseActivity, musicTrackDetails: MusicTrackDetails) {
            val intent = Intent(activity, TrackDetailsActivity::class.java)
            intent.putExtra("track", musicTrackDetails)
            activity.startActivity(intent)
        }

    }

    private var mMusicTrackDetails: MusicTrackDetails? = null

    private lateinit var img_album_poster: ImageView
    private lateinit var txt_track_title: TextView
    private lateinit var seek_track: AppCompatSeekBar

    private lateinit var img_prev: ImageButton
    private lateinit var img_backwards: ImageButton
    private lateinit var img_pause: ImageButton
    private lateinit var img_forwards: ImageButton
    private lateinit var img_next: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_details)

        initData()

        initActionbarWithBack(getString(R.string.track_details))

        initViews()

        setViews()

    }

    private fun initData() {
        mMusicTrackDetails = intent.getSerializableExtra("track") as MusicTrackDetails
    }

    private fun initViews() {
        img_album_poster = findViewById(R.id.img_album_poster)
        txt_track_title = findViewById(R.id.txt_track_title)
        seek_track = findViewById(R.id.seek_track)
        img_prev = findViewById(R.id.img_prev)
        img_backwards = findViewById(R.id.img_backwards)
        img_pause = findViewById(R.id.img_pause)
        img_forwards = findViewById(R.id.img_forwards)
        img_next = findViewById(R.id.img_next)
    }


    private fun setViews() {
        if (!mMusicTrackDetails?.musicFileThumb.isNullOrEmpty()) {
            Glide.with(this)
                    .load(mMusicTrackDetails?.musicFileThumb)
                    .into(img_album_poster)
        }
        if (!mMusicTrackDetails?.musicFileTitle.isNullOrEmpty()) {
            txt_track_title.text = mMusicTrackDetails?.musicFileTitle
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
