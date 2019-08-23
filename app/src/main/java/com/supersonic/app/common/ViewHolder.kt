
package com.supersonic.app.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBindView(model : Any)

}