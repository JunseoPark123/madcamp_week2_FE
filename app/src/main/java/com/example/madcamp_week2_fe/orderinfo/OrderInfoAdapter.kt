package com.example.madcamp_week2_fe.orderinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.R

class OrderInfoAdapter(private val orderList: List<OrderInfo>) :
    RecyclerView.Adapter<OrderInfoAdapter.OrderInfoViewHolder>() {


    class OrderInfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profileImage: ImageView = view.findViewById(R.id.profileImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.orderinfo_item, parent, false)
        return OrderInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderInfoViewHolder, position: Int) {
        val orderInfo = orderList[position]
        holder.profileImage.setImageResource(orderInfo.imageResourceId)
    }

    override fun getItemCount() = orderList.size


}