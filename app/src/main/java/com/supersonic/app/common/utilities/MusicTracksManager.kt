/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.common.utilities

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import com.supersonic.app.models.MusicTrackDetails
import java.lang.RuntimeException
import android.media.MediaPlayer
import com.supersonic.app.common.MyApplication


class MusicTracksManager : MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener {

    companion object {
        private const val TRACK_LIMIT = 5
    }

    private var mediaPlayer: MediaPlayer? = null

    //Milliseconds
    private val SEEK_TIME = 10000

    //IDs of tracks, managed until app is in RAM
    private var mTrackIdList = ArrayList<String>()

    //Presently Running trackAlbumId
    private var mRunningTrackId: String = ""

    private var mContentResolver: ContentResolver? = null

    init {
        mContentResolver = MyApplication.appInstance!!.contentResolver
    }


    fun initAllTracks(trackList: ArrayList<MusicTrackDetails>): Cursor {

        val cursor = mContentResolver!!.query(
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

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val musicTrack = MusicTrackDetails("")
                musicTrack.musicFileAlbum =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                musicTrack.musicFileArtist =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                musicTrack.musicFileTrackId =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))

                musicTrack.musicFileAlbumId =
                    java.lang.Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)))

                mTrackIdList.add(musicTrack.musicFileTrackId!!)

                musicTrack.musicFileTitle =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicTrack.musicFileDisplayName =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                musicTrack.musicFileUrl =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicTrack.musicFileDuration =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                musicTrack.musicFileYear =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR))
                trackList.add(musicTrack)
            }
        } else {
            // ERROR
            throw RuntimeException("Error while getting information of Music File")
        }

        return cursor
    }

    fun getTracksInBurst(trackList: ArrayList<MusicTrackDetails>): Cursor {

        val cursor = mContentResolver!!.query(
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

        if (cursor != null) {

            var count = 0

            while (cursor.moveToNext()) {
                val musicTrack = MusicTrackDetails("")
                musicTrack.musicFileAlbum =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                musicTrack.musicFileArtist =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                musicTrack.musicFileTrackId =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))

                musicTrack.musicFileAlbumId =
                    java.lang.Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)))

                mTrackIdList.add(musicTrack.musicFileTrackId!!)

                musicTrack.musicFileTitle =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicTrack.musicFileDisplayName =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                musicTrack.musicFileUrl =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicTrack.musicFileDuration =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                musicTrack.musicFileYear =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR))
                trackList.add(musicTrack)

                if (count < TRACK_LIMIT) {
                    count++
                } else {
                    return cursor
                }

            }
        } else {
            // ERROR
            throw RuntimeException("Error while getting information of Music File")
        }

        return cursor
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

        val cursor = mContentResolver!!.query(
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

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val musicTrack = MusicTrackDetails("")
                musicTrack.musicFileAlbum =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                musicTrack.musicFileArtist =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                musicTrack.musicFileTrackId =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK))

                musicTrack.musicFileAlbumId =
                    java.lang.Long.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)))

                musicTrack.musicFileTitle =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicTrack.musicFileDisplayName =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                musicTrack.musicFileUrl =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicTrack.musicFileDuration =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                musicTrack.musicFileYear =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR))
                stopAudio()
                mediaPlayer!!.reset()
                playAudio(musicTrack.musicFileUrl!!)
                cursor.close()
                return
            }
        } else {
            // ERROR
            throw RuntimeException("Error while getting information of Music File")
        }

    }


}