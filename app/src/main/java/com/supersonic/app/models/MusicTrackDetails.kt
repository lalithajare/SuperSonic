package com.supersonic.app.models

import java.io.Serializable

data class MusicTrackDetails(
    var musicFileTrackId: String? = ""
) : Serializable {
    var musicFileTitle: String? = ""
    var musicFileDisplayName: String? = ""
    var musicFileAlbumId: Long = 0L
    var musicFileArtist: String? = ""
    var musicFileThumb: String? = ""
    var musicFileAlbum: String? = ""
    var musicFileDuration: String? = ""
    var musicFileYear: String? = ""
    var musicFileUrl: String? = ""
    var musicFileSize: Long = 0L

}