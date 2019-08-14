package com.supersonic.app.common

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.supersonic.app.R
import com.supersonic.app.common.interfaces.TrackCallbacks
import com.supersonic.app.common.utilities.Constants
import com.supersonic.app.models.MusicTrackDetails
import com.supersonic.app.tracks.screens.TrackDetailsActivity

class MusicService() : Service() {

    private val mBinder = LocalBinder()

    private var mMusicTrackDetails: MusicTrackDetails? = null


    var trackCallbacks: TrackCallbacks? = null

    companion object {
        private var isTrackRunning = false
        private var mTracksManager = MyApplication.appInstance!!.trackManager

        class NotificationCloseButtonHandler : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                Toast.makeText(context, "Close Clicked", Toast.LENGTH_SHORT).show()

            }
        }

        class NotificationPlaybackHandler : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {

                Toast.makeText(context, "Playback Clicked", Toast.LENGTH_SHORT).show()
                if (isTrackRunning) {
                    mTracksManager?.pauseAudio()
                    isTrackRunning = false
                } else {
                    mTracksManager?.resumeAudio()
                    isTrackRunning = true
                }

            }
        }

    }


    override fun onBind(intent: Intent): IBinder {

        mMusicTrackDetails = intent.getSerializableExtra(Constants.MUSIC_OBJ) as MusicTrackDetails

        mTracksManager?.initMedia()

        return mBinder
    }

    fun playTrack() {

        mTracksManager?.playAudio(mMusicTrackDetails?.musicFileUrl!!)

        trackCallbacks?.onTrackLoaded()

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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notificationIntent = Intent(this, TrackDetailsActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, 0
        )
        val notificationView = RemoteViews(this.packageName, R.layout.layout_track_notification)

        val buttonCloseIntent =
            Intent(this, MusicService.Companion.NotificationCloseButtonHandler::class.java)
        buttonCloseIntent.putExtra("action", "close")
        val buttonClosePendingIntent = PendingIntent.getBroadcast(this, 0, buttonCloseIntent, 0)
        notificationView.setOnClickPendingIntent(R.id.img_close, buttonClosePendingIntent)
        notificationView.setOnClickPendingIntent(R.id.rel_notification_layout, pendingIntent)

        val icon = BitmapFactory.decodeResource(
            resources,
            R.mipmap.ic_launcher
        )

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "RBL_CHANNEL_13310"
        val notification: Notification

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Foreground service notification channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                );

            notificationManager.createNotificationChannel(notificationChannel)
            notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationView)
                .setCustomBigContentView(notificationView)
                .setOngoing(true).build()
        } else {
            notification = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationView)
                .setCustomBigContentView(notificationView)
                .setOngoing(true).build()
        }

        startForeground(
            101,
            notification
        )

        return START_STICKY
    }

    inner class LocalBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

}
