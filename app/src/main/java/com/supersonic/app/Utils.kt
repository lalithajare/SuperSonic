package com.supersonic.app

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import java.io.File
import java.lang.RuntimeException

object Utils {
    val musicTrack = MusicTrackDetails(0L)

    fun getMusicDetails(context: Context, file: File): MusicTrackDetails {
        val path = file.canonicalPath
        val c = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TRACK,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.YEAR
            ),
            MediaStore.Audio.Media.DATA + " = ?",
            arrayOf(path),
            ""
        )

        if (c != null) {
            while (c.moveToNext()) {
                musicTrack.musicFileAlbum = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                musicTrack.musicFileArtist = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                musicTrack.musicFileTrack = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TRACK))
                musicTrack.musicFileTitle = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicTrack.musicFileDisplayName = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                musicTrack.musicFileData = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicTrack.musicFileDuration = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DURATION))
                musicTrack.musicFileYear = c.getString(c.getColumnIndex(MediaStore.Audio.Media.YEAR))
            }
        } else {
            // ERROR
            throw RuntimeException("Error while getting information of Music File")
        }
        return musicTrack
    }

}