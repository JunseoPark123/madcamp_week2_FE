package com.example.madcamp_week2_fe.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.databinding.CartItemBinding

class CartAdapter(private val context: Context, val items: MutableList<CartItem>, private val onDeleteItem: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            val imageResId = when (item.product_name) {
                "비타민 과일 박스 세트" -> R.drawable.image1
                "매콤달콤 진미채 박스" -> R.drawable.image2
                "명절 반찬 박스 세트" -> R.drawable.image3
                "영양소 상큼 과일 세트" -> R.drawable.image4
                "채소 세트" -> R.drawable.image5
                else -> R.drawable.image6
            }
            binding.image.setImageResource(imageResId)
            binding.menu.text = item.product_name
            binding.storeName.text = item.store_name
            binding.price.text = "${item.price}원"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = CartItemBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.binding.trash.setOnClickListener { // 'trash'는 휴지통 ImageView의 ID
            onDeleteItem(position) // 클릭 시 해당 포지션의 아이템 삭제
        }
    }

    override fun getItemCount() = items.size
}