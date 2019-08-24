/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

package com.supersonic.app.common

import android.content.Context
import android.view.View

abstract class BaseViewMvc : ViewMvc {

    private lateinit var mRootView: View

    fun setRootView(rootView: View) {
        mRootView = rootView
    }

    override fun getRootView(): View {
        return mRootView
    }

    fun <T : View> findViewById(id: Int): T {
        return getRootView().findViewById(id)
    }

    fun getContext(): Context? {
        return getRootView().context
    }
}