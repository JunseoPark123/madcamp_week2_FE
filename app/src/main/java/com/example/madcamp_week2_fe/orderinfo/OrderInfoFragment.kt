package com.example.madcamp_week2_fe.orderinfo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.MainActivity
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.RetrofitClient
import com.example.madcamp_week2_fe.TAG_HOME
import com.example.madcamp_week2_fe.home.CartAdapter
import com.example.madcamp_week2_fe.home.HomeFragment
import com.example.madcamp_week2_fe.interfaces.CartApiService
import com.example.madcamp_week2_fe.interfaces.StarApiService
import com.example.madcamp_week2_fe.models.RatingRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderInfoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderInfoAdapter: OrderInfoAdapter

    private lateinit var accessToken: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPreferences =
            activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        accessToken = sharedPreferences?.getString("access_token", "") ?: ""

        val view = inflater.inflate(R.layout.fragment_orderinfo, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        loadOrderInfo()

        val leftArrow: ImageView = view.findViewById(R.id.left_arrow)
        leftArrow.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(TAG_HOME)
        }

        return view
    }


    private fun loadOrderInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.getInstance().create(CartApiService::class.java)
                    .getOrderHistory("Bearer $accessToken")
                if (response.isSuccessful && response.body() != null) {
                    val orders = response.body()!!
                    val orderInfos = orders.asReversed().map { order ->
                        OrderInfo(
                            R.drawable.image1,
                            order.product_name,
                            order.store_name,
                            order.price,
                            order.created_at
                        )
                    }
                    withContext(Dispatchers.Main) {
                        orderInfoAdapter = OrderInfoAdapter(orderInfos, accessToken) { storeName ->
                            showRatingDialog(storeName)
                        }
                        recyclerView.adapter = orderInfoAdapter
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("OrderInfoFragment", "Error loading order history: ${e.message}")
                    // You can also show a Toast or update the UI to indicate an error here
                }
            }
        }
    }

    private fun showRatingDialog(storeName: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_rating, null)
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.ratingBar)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("별점 제출")

        dialogBuilder.setPositiveButton("제출") { dialog, _ ->
            val rating = ratingBar.rating
            submitRating(storeName, rating)
            dialog.dismiss()
        }
        dialogBuilder.create().show()
    }

    private fun submitRating(storeName: String, rating: Float) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val starApi = RetrofitClient.getInstance().create(StarApiService::class.java)
                val response = starApi.postRating(
                    "Bearer $accessToken",
                    RatingRequest(storeName, rating.toInt())
                )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "별점이 제출되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "별점 제출에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "오류가 발생했습니다: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}