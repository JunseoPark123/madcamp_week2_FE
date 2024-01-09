package com.example.madcamp_week2_fe.dibs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.R

class DibsAdapter(private val items: List<DibsItem>) : RecyclerView.Adapter<DibsAdapter.DibsViewHolder>() {

    class DibsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val storeNameTextView: TextView = view.findViewById(R.id.storeName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DibsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dibs_item, parent, false)
        return DibsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DibsViewHolder, position: Int) {
        val item = items[position]
        holder.storeNameTextView.text = item.storeName

    }

    override fun getItemCount(): Int {
        return items.size
    }
}