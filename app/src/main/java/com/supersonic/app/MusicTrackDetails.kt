package com.supersonic.app

import java.io.Serializable

data class MusicTrackDetails(var id: Long) : Serializable {
    var musicFileTitle: String? = ""
    var musicFileAlbumId: Long = 0L
    var musicFileDisplayName: String? = ""
    var musicFileArtist: String? = ""
    var musicFileThumb: String? = ""
    var musicFileTrack: String? = ""
    var musicFileAlbum: String? = ""
    var musicFileDuration: String? = ""
    var musicFileYear: String? = ""
    var musicFileUrl: String? = ""
    var musicFileSize: Long = 0L

}