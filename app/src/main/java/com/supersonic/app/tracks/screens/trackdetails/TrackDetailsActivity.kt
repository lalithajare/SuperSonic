package com.supersonic.app.tracks.screens.trackdetails

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import com.bumptech.glide.Glide
import com.supersonic.app.common.BaseActivity
import com.supersonic.app.models.MusicTrackDetails
import com.supersonic.app.R
import com.supersonic.app.common.MusicService
import com.supersonic.app.common.interfaces.TrackCallbacks
import com.supersonic.app.common.utilities.Constants

class TrackDetailsActivity : BaseActivity(), View.OnClickListener, TrackCallbacks {

    companion object ActivtyStarter {

        fun beginWith(activity: BaseActivity, musicTrackDetails: MusicTrackDetails) {
            val intent = Intent(activity, TrackDetailsActivity::class.java)
            intent.putExtra("track", musicTrackDetails)
            activity.startActivity(intent)
        }

    }

    private var mService: MusicService? = null
    private var mBound: Boolean = false
    private var isTrackPlaying: Boolean = false
    private var mMusicTrackDetails: MusicTrackDetails? = null


    private lateinit var imgAlbumPoster: ImageView
    private lateinit var txtTrackTitle: TextView
    private lateinit var seekTrack: AppCompatSeekBar
    private lateinit var imgPrev: ImageButton
    private lateinit var imgBackwards: ImageButton
    private lateinit var imgPlayBack: ImageButton
    private lateinit var imgForwards: ImageButton
    private lateinit var imgNext: ImageButton


    /** Defines callbacks for service binding, passed to bindService() */
    private val mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
            mService?.stopTrack()
        }


        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as MusicService.LocalBinder

            mService = binder.getService()

            mBound = true

            mService?.trackCallbacks = this@TrackDetailsActivity
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_details)

        initData()

        initActionbarWithBack(getString(R.string.track_details))

        initViews()

        setViews()

        initService()

    }

    private fun initService() {
        val intent = Intent(this@TrackDetailsActivity, MusicService::class.java)
        intent.putExtra(Constants.MUSIC_OBJ, mMusicTrackDetails)
        intent.action = Constants.ACTION.STARTFOREGROUND_ACTION
        startService(intent)
        bindService(intent, mConnection, 0)
    }

    private fun initData() {
        mMusicTrackDetails = intent.getSerializableExtra("track") as MusicTrackDetails
    }

    private fun initViews() {
        imgAlbumPoster = findViewById(R.id.img_album_poster)
        txtTrackTitle = findViewById(R.id.txt_track_title)
        seekTrack = findViewById(R.id.seek_track)
        imgPrev = findViewById(R.id.img_prev)
        imgBackwards = findViewById(R.id.img_backwards)
        imgPlayBack = findViewById(R.id.img_playback)
        imgForwards = findViewById(R.id.img_forwards)
        imgNext = findViewById(R.id.img_next)
    }


    private fun setViews() {
        if (!mMusicTrackDetails?.musicFileThumb.isNullOrEmpty()) {
            Glide.with(this)
                .load(mMusicTrackDetails?.musicFileThumb)
                .into(imgAlbumPoster)
        }
        if (!mMusicTrackDetails?.musicFileTitle.isNullOrEmpty()) {
            txtTrackTitle.text = mMusicTrackDetails?.musicFileTitle
        }

        imgPrev.setOnClickListener(this)

        imgBackwards.setOnClickListener(this)

        imgPlayBack.setOnClickListener(this)

        imgForwards.setOnClickListener(this)

        imgNext.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.img_playback -> {
                if (mBound) {
                    if (isTrackPlaying) {
                        imgPlayBack.setImageResource(android.R.drawable.ic_media_play)
                        mService?.pauseTrack()
                    } else {
                        imgPlayBack.setImageResource(android.R.drawable.ic_media_pause)
                        mService?.resumeAudio()
                    }
                } else {
                    initService()
                    imgPlayBack.setImageResource(android.R.drawable.ic_media_pause)
                }
            }

            R.id.img_backwards -> {
                mService?.backwardTrack()
            }

            R.id.img_forwards -> {
                mService?.forwardTrack()
            }


            R.id.img_prev -> {
                mService?.prevTrack()
            }

            R.id.img_next -> {
                mService?.nextTrack()
            }

        }
    }

    //Extension function for boolean
//    infix fun <T> Boolean.then(param: T): T? = if (this) param else null

    override fun onTrackLoaded() {
        isTrackPlaying = true
        imgPlayBack.setImageResource(android.R.drawable.ic_media_pause)
    }

    override fun onTrackStopped() {
        isTrackPlaying = false
        imgPlayBack.setImageResource(android.R.drawable.ic_media_play)
    }

    override fun onTrackFinished() {
        isTrackPlaying = false
        imgPlayBack.setImageResource(android.R.drawable.ic_media_play)
    }

    override fun onTrackPaused() {
        isTrackPlaying = false
        imgPlayBack.setImageResource(android.R.drawable.ic_media_play)
    }

    override fun onTrackResumed() {
        isTrackPlaying = true
        imgPlayBack.setImageResource(android.R.drawable.ic_media_pause)
    }

    override fun onTrackFFW() {
    }

    override fun onTrackREW() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mService?.unbindService(mConnection)
    }

}
