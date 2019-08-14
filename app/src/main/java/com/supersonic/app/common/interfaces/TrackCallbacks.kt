package com.supersonic.app.common.interfaces

interface TrackCallbacks {

    fun onTrackLoaded()

    fun onTrackFinished()

    fun onTrackPaused()

    fun onTrackResumed()

    fun onTrackFFW()

    fun onTrackREW()
}