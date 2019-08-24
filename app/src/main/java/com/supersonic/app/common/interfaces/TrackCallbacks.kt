/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.common.interfaces

interface TrackCallbacks {

    fun onTrackLoaded()

    fun onTrackFinished()

    fun onTrackStopped()

    fun onTrackPaused()

    fun onTrackResumed()

    fun onTrackFFW()

    fun onTrackREW()
}