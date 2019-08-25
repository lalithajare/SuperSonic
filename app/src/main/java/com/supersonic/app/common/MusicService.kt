/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.common

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.supersonic.app.R
import com.supersonic.app.common.interfaces.TrackCallbacks
import com.supersonic.app.common.utilities.Constants
import com.supersonic.app.models.MusicTrackDetails
import com.supersonic.app.tracks.screens.tracklist.TracksListActivity

class MusicService : Service() {

    private val mBinder = LocalBinder()

    private var mMusicTrackDetails: MusicTrackDetails? = null


    var trackCallbacks: TrackCallbacks? = null

    companion object {
        private var isTrackRunning = false
        private var mTracksManager = MyApplication.appInstance!!.mTrackManagerMusic

        class NotificationCloseButtonHandler : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                Toast.makeText(context, "Close Clicked", Toast.LENGTH_SHORT).show()

            }
        }

    }


    override fun onBind(intent: Intent): IBinder {

        mMusicTrackDetails = intent.getSerializableExtra(Constants.MUSIC_OBJ) as MusicTrackDetails

        mTracksManager?.initMedia()

        playTrack()

        return mBinder
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent?.action.equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            val notificationIntent = Intent(this, TracksListActivity::class.java)
//        notificationIntent.action = Constants.ACTION.MAIN_ACTION
//        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(
                this, 0,
                notificationIntent, 0
            )

            val closeIntent = Intent(this, MusicService::class.java)
            closeIntent.action = Constants.ACTION.STOPFOREGROUND_ACTION

            val pCloseIntent = PendingIntent.getService(
                this, 0,
                closeIntent, 0
            )

            val notification = NotificationCompat.Builder(this)
                .setContentTitle("Truiton Music Player")
                .setTicker("Truiton Music Player")
                .setContentText("My Music")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(
                    android.R.drawable.ic_menu_close_clear_cancel,
                    "Close", pCloseIntent
                ).build()
            startForeground(
                Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                notification
            )

        } else if (intent?.action.equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i("MusicService", "Received Stop Foreground Intent")
            stopForeground(true)
            stopSelf()
        }


        return START_NOT_STICKY
    }

    inner class LocalBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }


    fun playTrack() {

        mTracksManager?.playAudio(mMusicTrackDetails?.musicFileUrl!!)

        trackCallbacks?.onTrackLoaded()

        isTrackRunning = true

    }

    fun stopTrack() {

        mTracksManager?.stopAudio()

        trackCallbacks?.onTrackStopped()

        isTrackRunning = true

    }

    fun pauseTrack() {
        mTracksManager?.pauseAudio()

        trackCallbacks?.onTrackPaused()

        isTrackRunning = false
    }

    fun resumeAudio() {
        mTracksManager?.resumeAudio()

        trackCallbacks?.onTrackResumed()

        isTrackRunning = true
    }

    fun forwardTrack() {
        mTracksManager?.seekAudioFwd()
    }

    fun backwardTrack() {
        mTracksManager?.seekAudioBwd()
    }

    fun nextTrack() {
        mTracksManager?.nextAudio()
    }

    fun prevTrack() {
        mTracksManager?.previousAudio()
    }

}
