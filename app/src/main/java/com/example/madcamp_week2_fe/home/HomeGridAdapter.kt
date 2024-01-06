package com.example.madcamp_week2_fe.home

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.madcamp_week2_fe.R

class HomeGridAdapter(private val context: Context, private val items: List<HomeGridItem>) : BaseAdapter() {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.home_grid_item, parent, false)

        val item = items[position]
        val imageView = view.findViewById<ImageView>(R.id.image)
        val textView = view.findViewById<TextView>(R.id.text)

        imageView.setImageResource(item.imageResource)
        textView.text = item.text

        return view
    }
}