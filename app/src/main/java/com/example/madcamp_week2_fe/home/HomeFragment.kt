package com.example.madcamp_week2_fe.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.madcamp_week2_fe.ready.LocationActivity
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.RetrofitClient
import com.example.madcamp_week2_fe.interfaces.StoreApiService
import com.example.madcamp_week2_fe.interfaces.UserApiService
import com.example.madcamp_week2_fe.ready.LoginActivity
import kotlinx.coroutines.*
import java.lang.Exception

class HomeFragment : Fragment() {
    private val storeApi: StoreApiService = RetrofitClient.getInstance().create(StoreApiService::class.java)
    private var items: MutableList<HomeGridItem> = mutableListOf()
    private var accessToken: String? = null
    private var allItems: MutableList<HomeGridItem> = mutableListOf()
    private lateinit var gridView: GridView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Accessing SharedPreferences to get the access_token
        val sharedPreferences = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        accessToken = sharedPreferences?.getString("access_token", null)
        val locationInfo = sharedPreferences?.getString("location_info", "기본 위치") // 기본값 설정

        val locationInfoTextView: TextView = view.findViewById(R.id.locationinfo)
        locationInfoTextView.text = locationInfo ?: "기본 위치"


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

        val enroll : ImageView = view.findViewById(R.id.redrightarrow)
        enroll.setOnClickListener {
            val enrollUrl = "http://ec2-3-34-151-36.ap-northeast-2.compute.amazonaws.com/store/add_store_menu/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(enrollUrl))
            startActivity(intent)
        }


        val nameSearch: EditText = view.findViewById(R.id.namesearch)
        nameSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterItems(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        gridView = view.findViewById(R.id.homeGridView)


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
                    allItems.clear()
                    allItems.addAll(newItems) // allItems 초기화
                    items.clear()
                    items.addAll(newItems) // items 초기화
                    gridView.adapter = HomeGridAdapter(requireContext(), items) // 어댑터 초기화
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



    private fun filterItems(searchQuery: String) {
        val filteredList = allItems.filter {
            it.store?.contains(searchQuery, ignoreCase = true) == true ||
                    it.menuName?.contains(searchQuery, ignoreCase = true) == true ||
                    it.detailName1?.contains(searchQuery, ignoreCase = true) == true ||
                    it.detailName2?.contains(searchQuery, ignoreCase = true) == true
        }
        items.clear()
        items.addAll(filteredList)
        val adapter = HomeGridAdapter(requireContext(), items)
        gridView.adapter = adapter
        adapter.notifyDataSetChanged()
    }


    private fun navigateToLoginActivity() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish() // 현재 액티비티 종료
    }
}