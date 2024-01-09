package com.example.madcamp_week2_fe.orderinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.databinding.OrderinfoItemBinding

class OrderInfoAdapter(private val orderList: List<OrderInfo>) :
    RecyclerView.Adapter<OrderInfoAdapter.OrderInfoViewHolder>() {

    class OrderInfoViewHolder(val binding: OrderinfoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderInfo: OrderInfo) {
            binding.profileImage.setImageResource(orderInfo.imageResourceId)
            binding.menu.text = orderInfo.product_name
            binding.store.text = orderInfo.store_name
            binding.price.text = "${orderInfo.price}원"
            binding.date.text = orderInfo.created_at
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderInfoViewHolder {
        val binding = OrderinfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderInfoViewHolder, position: Int) {
        val orderInfo = orderList[position]
        holder.bind(orderInfo.apply {
            // created_at을 10자로 제한
            created_at = created_at.take(10)
        })
    }

    override fun getItemCount() = orderList.size
}