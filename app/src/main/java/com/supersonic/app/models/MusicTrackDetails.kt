/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

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