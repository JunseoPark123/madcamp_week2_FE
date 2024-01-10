package com.example.madcamp_week2_fe.orderinfo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
                val response = RetrofitClient.getInstance().create(CartApiService::class.java).getOrderHistory("Bearer $accessToken")
                if (response.isSuccessful && response.body() != null) {
                    val orders = response.body()!!
                    val orderInfos = orders.asReversed().map { order -> // 역순으로 정렬
                        OrderInfo(
                            R.drawable.image1,
                            order.product_name,
                            order.store_name,
                            order.price,
                            order.created_at
                        )
                    }
                    withContext(Dispatchers.Main) {
                        orderInfoAdapter = OrderInfoAdapter(orderInfos)
                        recyclerView.adapter = orderInfoAdapter
                    }
                } else {
                    Log.e("OrderInfoFragment", "Failed to load order history: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("OrderInfoFragment", "Error in loading order history: ${e.message}")
            }
        }
    }



    private fun navigateToHomeFragment() {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, HomeFragment())
            .addToBackStack(null)  // 옵션: 뒤로 가기 스택에 추가
            .commit()
    }
}