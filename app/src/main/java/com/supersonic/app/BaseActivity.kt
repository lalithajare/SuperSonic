package com.supersonic.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {

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

}
