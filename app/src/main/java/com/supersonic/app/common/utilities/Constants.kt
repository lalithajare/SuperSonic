/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.common.utilities

object Constants {

    val MUSIC_OBJ = "music_object"

    interface ACTION {
        companion object {
            val MAIN_ACTION = "com.truiton.foregroundservice.action.main"
            val PREV_ACTION = "com.truiton.foregroundservice.action.prev"
            val PLAY_ACTION = "com.truiton.foregroundservice.action.play"
            val NEXT_ACTION = "com.truiton.foregroundservice.action.next"
            val STARTFOREGROUND_ACTION = "com.truiton.foregroundservice.action.startforeground"
            val STOPFOREGROUND_ACTION = "com.truiton.foregroundservice.action.stopforeground"
        }
    }

    interface NOTIFICATION_ID {
        companion object {
            val FOREGROUND_SERVICE = 101
        }
    }

}