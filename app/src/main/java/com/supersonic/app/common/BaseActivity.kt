/*
 *  Created by Mr. Lalit Nandakumar Hajare
 *  This code demonstrates the coding capabilities of Mr. Lalit Nandakumar Hajare.
 *  The sole purpose of this application is to help recruiters showcase the skills that I,
 *   Mr. Lalit Nandakumar Hajare, posses.
 */

/*
 *
 */

package com.supersonic.app.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.supersonic.app.R
import com.supersonic.app.common.dependencyinjection.CompositionRoot
import com.supersonic.app.common.dependencyinjection.ControllerCompositionRoot

open class BaseActivity : AppCompatActivity() {

    private var mControllerCompositionRoot: ControllerCompositionRoot? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun initActionbar(title: String) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val txtTitle = findViewById<TextView>(R.id.txt_title)
        txtTitle.text = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }

    protected fun initActionbarWithBack(title: String) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val txtTitle = findViewById<TextView>(R.id.txt_title)
        txtTitle.text = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.mipmap.back)
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }

    protected fun getCompositionRoot(): ControllerCompositionRoot {

        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot =
                ControllerCompositionRoot((application as MyApplication).getCompositionRoot(), this)
        }
        return mControllerCompositionRoot!!
    }

}
