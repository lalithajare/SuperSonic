/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.common

import com.supersonic.app.tracks.screens.tracklist.TrackListMvc
import java.util.*
import kotlin.collections.HashSet

abstract class BaseObservableViewMvc<ListenerType> : BaseViewMvc(), ObservableViewMvc<ListenerType> {

    private val mListeners = HashSet<ListenerType>()

    override fun registerListener(listener: ListenerType) {
        mListeners.add(listener)
    }

    override fun unregisterListener(listener: ListenerType) {
        mListeners.remove(listener)
    }

    /**
     * Defensive programming to not allow modifications of Listeners
     */
    fun getListeners(): Set<ListenerType> {
        return Collections.unmodifiableSet(mListeners)
    }

}