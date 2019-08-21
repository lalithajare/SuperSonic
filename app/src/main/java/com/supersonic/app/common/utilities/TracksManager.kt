package com.supersonic.app.common.utilities

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import com.supersonic.app.models.MusicTrackDetails
import java.lang.RuntimeException
import android.media.MediaPlayer


class TracksManager(var contentResolver: ContentResolver) : MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener {


    private var mediaPlayer: MediaPlayer? = null

    //Milliseconds
    private val SEEK_TIME = 10000

    //IDs of tracks, managed until app is in RAM
    private var mTrackIdList = ArrayList<String>()

    //Presently Running trackAlbumId
    private var mRunningTrackId: String = ""


    fun initAllTracks(trackList: ArrayList<MusicTrackDetails>): Cursor {
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
                val musicTrack = MusicTrackDetails("")
                musicTrack.musicFileAlbum = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                musicTrack.musicFileArtist = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                musicTrack.musicFileTrackId = c.getString(c.getColumnIndex(MediaStore.Audio.Media._ID))

                musicTrack.musicFileAlbumId =
                    java.lang.Long.valueOf(c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)))

                mTrackIdList.add(musicTrack.musicFileTrackId!!)

                musicTrack.musicFileTitle = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicTrack.musicFileDisplayName = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                musicTrack.musicFileUrl = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicTrack.musicFileDuration = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DURATION))
                musicTrack.musicFileYear = c.getString(c.getColumnIndex(MediaStore.Audio.Media.YEAR))
                trackList.add(musicTrack)
            }
        } else {
            // ERROR
            throw RuntimeException("Error while getting information of Music File")
        }

        return c
    }

    fun initMedia() {
        //set up MediaPlayer
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setOnPreparedListener(this)
        mediaPlayer!!.setOnCompletionListener(this)
    }

    override fun onPrepared(mp: MediaPlayer?) {

    }

    override fun onCompletion(mp: MediaPlayer?) {

    }

    fun playAudio(path: String) {
        try {
            mediaPlayer!!.setDataSource(path)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopAudio() {
        mediaPlayer!!.stop()
    }

    fun pauseAudio() {
        mediaPlayer!!.pause()
    }

    fun resumeAudio() {
        mediaPlayer!!.start()
    }

    fun seekAudioFwd() {
        mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition + SEEK_TIME)
    }

    fun seekAudioBwd() {
        mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition - SEEK_TIME)
    }

    fun nextAudio() {
        var currentIndex = mTrackIdList.indexOf(mRunningTrackId)
        if (currentIndex == mTrackIdList.size - 1) {
            currentIndex = 0
        }
        mRunningTrackId = mTrackIdList.get(currentIndex + 1)
        changeAudio(mRunningTrackId)
    }

    fun previousAudio() {
        var currentIndex = mTrackIdList.indexOf(mRunningTrackId)
        if (currentIndex == 0) {
            currentIndex = (mTrackIdList.size - 1)
        }
        mRunningTrackId = mTrackIdList.get(currentIndex - 1)
        changeAudio(mRunningTrackId)
    }

    private fun changeAudio(trackId: String) {

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
            "${MediaStore.Audio.Media._ID} = ?",
            arrayOf(trackId),
            null
        )

        if (c != null) {
            while (c.moveToNext()) {
                val musicTrack = MusicTrackDetails("")
                musicTrack.musicFileAlbum = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                musicTrack.musicFileArtist = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                musicTrack.musicFileTrackId = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TRACK))

                musicTrack.musicFileAlbumId =
                    java.lang.Long.valueOf(c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)))

                musicTrack.musicFileTitle = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicTrack.musicFileDisplayName = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                musicTrack.musicFileUrl = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicTrack.musicFileDuration = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DURATION))
                musicTrack.musicFileYear = c.getString(c.getColumnIndex(MediaStore.Audio.Media.YEAR))

                stopAudio()

                mediaPlayer!!.reset()

                playAudio(musicTrack.musicFileUrl!!)

                return

            }
        } else {
            // ERROR
            throw RuntimeException("Error while getting information of Music File")
        }

    }

}