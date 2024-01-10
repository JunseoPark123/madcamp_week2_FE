package com.example.madcamp_week2_fe.orderinfo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.RetrofitClient
import com.example.madcamp_week2_fe.databinding.OrderinfoItemBinding
import com.example.madcamp_week2_fe.interfaces.StarApiService
import com.example.madcamp_week2_fe.models.AverageRatingRequest
import com.example.madcamp_week2_fe.models.RatingRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderInfoAdapter(
    private val orderList: List<OrderInfo>,
    private val accessToken: String,
    private val onReviewClick: (String) -> Unit
) : RecyclerView.Adapter<OrderInfoAdapter.OrderInfoViewHolder>() {

    class OrderInfoViewHolder(val binding: OrderinfoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderInfo: OrderInfo) {
            // 상품 이름에 따라 이미지를 설정합니다.
            val imageResId = when (orderInfo.product_name) {
                "비타민 과일 박스 세트" -> R.drawable.image1
                "매콤달콤 진미채 박스" -> R.drawable.image2
                "명절 반찬 박스 세트" -> R.drawable.image3
                "영양소 상큼 과일 세트" -> R.drawable.image4
                "채소 세트" -> R.drawable.image5
                else -> R.drawable.image6
            }
            binding.profileImage.setImageResource(imageResId)
            binding.menu.text = orderInfo.product_name
            binding.store.text = orderInfo.store_name
            binding.price.text = "${orderInfo.price}원"
            binding.date.text = orderInfo.created_at.take(10) // created_at을 10자로 제한
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderInfoViewHolder {
        val binding = OrderinfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderInfoViewHolder, position: Int) {
        val orderInfo = orderList[position]
        holder.bind(orderInfo)

        holder.binding.review.setOnClickListener {
            onReviewClick(orderInfo.store_name)
        }

        // 평균 별점을 가져오고 화면에 표시합니다.
        getAverageRating(orderInfo.store_name, holder.binding.rate, holder.itemView.context)
    }




    private fun getAverageRating(storeName: String, textView: TextView, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val starApi = RetrofitClient.getInstance().create(StarApiService::class.java)
                val response = starApi.getAverageRating(AverageRatingRequest(storeName))
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val averageRating = response.body()?.average_rating ?: 0f
                        textView.text = "별점: ${String.format("%.1f", averageRating)}"
                    }
                }
            } catch (e: Exception) {
                Log.e("OrderInfoAdapter", "평균 별점 조회 실패: ${e.message}")
            }
        }
    }

    override fun getItemCount() = orderList.size
}