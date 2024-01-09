package com.example.madcamp_week2_fe.dibs

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.MainActivity
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.RetrofitClient
import com.example.madcamp_week2_fe.TAG_HOME
import com.example.madcamp_week2_fe.interfaces.CartApiService
import com.example.madcamp_week2_fe.interfaces.StoreApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DibsFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var storeApi: StoreApiService
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var accessToken: String
    private var userName: String? = null

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dibs, container, false)

        storeApi = RetrofitClient.getInstance().create(StoreApiService::class.java)
        val sharedPrefs = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        var accessToken = sharedPrefs.getString("access_token", null) ?: ""
        userName = sharedPrefs.getString("username", null)
        val userText : TextView = view.findViewById(R.id.userinfo)
        userText.text = userName
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val leftArrow: ImageView = view.findViewById(R.id.left_arrow)
        leftArrow.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(TAG_HOME)
        }

        loadStores()
//        loadOrderHistory()
        return view
    }

//    override fun onResume() {
//        super.onResume()
//        loadStores()
//        loadOrderHistory()
//    }



//    private fun loadOrderHistory() {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response = RetrofitClient.getInstance().create(CartApiService::class.java)
//                    .getOrderHistory("Bearer $accessToken")
//                if (response.isSuccessful && response.body() != null) {
//                    val orderCount = response.body()!!.size
//                    withContext(Dispatchers.Main) {
//                        val orderAmountTextView : TextView? = view?.findViewById(R.id.orderamount)
//                        orderAmountTextView?.text = orderCount.toString() // 주문 개수 업데이트
//                        Log.d("DibsFragment", "Order history loaded: $orderCount orders") // 성공 로그
//                    }
//                } else {
//                    Log.e("DibsFragment", "Failed to load order history: ${response.errorBody()?.string()}") // 실패 로그
//                }
//            } catch (e: Exception) {
//                Log.e("DibsFragment", "Error in loading order history: ${e.message}") // 예외 로그
//            }
//        }
//    }

    private fun loadStores() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val stores = storeApi.getAllStores()
                val dibsItems = stores.map { (storeName, favoriteStatus) ->
                    DibsItem(storeName, getFavoriteStatus(storeName))
                }.filter { it.favoriteStatus == 1 }

                withContext(Dispatchers.Main) {
                    recyclerView.adapter = DibsAdapter(dibsItems)
                }
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }

    private fun getFavoriteStatus(storeName: String): Int {
        val sharedPrefs = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val accessToken = sharedPrefs.getString("access_token", null) ?: ""
        return sharedPrefs.getInt("$accessToken-$storeName", 0)
    }
}