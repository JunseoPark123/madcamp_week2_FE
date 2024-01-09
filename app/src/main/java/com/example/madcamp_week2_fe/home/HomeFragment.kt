package com.example.madcamp_week2_fe.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.madcamp_week2_fe.ready.LocationActivity
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.RetrofitClient
import com.example.madcamp_week2_fe.interfaces.StoreApiService
import kotlinx.coroutines.*
import java.lang.Exception

class HomeFragment : Fragment() {
    private val storeApi: StoreApiService = RetrofitClient.getInstance().create(StoreApiService::class.java)
    private var items: MutableList<HomeGridItem> = mutableListOf()
    private var accessToken: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Accessing SharedPreferences to get the access_token
        val sharedPreferences = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        accessToken = sharedPreferences?.getString("access_token", null)
        Log.d("MainActivity", "AccessToken retrieved: $accessToken")

        val location: ImageView = view.findViewById(R.id.location)
        location.setOnClickListener {
            val intent = Intent(activity, LocationActivity::class.java)
            startActivity(intent)
        }

        val cart: ImageView = view.findViewById(R.id.cart)
        cart.setOnClickListener {
            val intent = Intent(activity, CartActivity::class.java).apply {
                putExtra("access_token", accessToken) // Passing access_token to CartActivity
            }
            startActivity(intent)
        }

        val gridView: GridView = view.findViewById(R.id.homeGridView)


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val menus = storeApi.getAllMenus()
                Log.d("HomeFragment", "API 호출 성공, 메뉴 수: ${menus.size}")
                val newItems = menus.map { menu ->
                    HomeGridItem(
                        imageResource = R.drawable.image1,
                        store = menu.store_name,
                        menuName = menu.name,
                        detailName1 = menu.details.detail_name1,
                        detailGram1 = menu.details.detail_gram1,
                        detailName2 = menu.details.detail_name2,
                        detailGram2 = menu.details.detail_gram2,
                        detailName3 = menu.details.detail_name3,
                        detailGram3 = menu.details.detail_gram3,
                        amount = menu.remaining_quantity,
                        price = menu.price,
                        )

                }
                withContext(Dispatchers.Main) {
                    items.clear()
                    items.addAll(newItems)
                    Log.d("HomeFragment", "아이템 설정 완료, 아이템 수: ${items.size}")
                    gridView.adapter = HomeGridAdapter(requireContext(), items)
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "API 호출 실패", e)
            }
        }


        gridView.setOnItemClickListener { _, _, position, _ ->
            val item = items[position] // 여기에서 멤버 변수 사용
            val intent = Intent(activity, ItemInfoActivity::class.java).apply {
                // 필요한 경우 loginResponse의 데이터를 Intent에 추가
                putExtra("access_token", accessToken)
                putExtra("store", item.store)
                putExtra("menuName", item.menuName)
                putExtra("detailName1", item.detailName1)
                putExtra("detailGram1", item.detailGram1)
                putExtra("detailName2", item.detailName2)
                putExtra("detailGram2", item.detailGram2)
                putExtra("detailName3", item.detailName3)
                putExtra("detailGram3", item.detailGram3)
                putExtra("amount", item.amount)
                putExtra("price", item.price)
            }
            // 필요한 경우, item의 정보를 Intent에 추가
            startActivity(intent)
        }

        return view
    }
}