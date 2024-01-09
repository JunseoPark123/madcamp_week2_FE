package com.example.madcamp_week2_fe.dibs

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.MainActivity
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.RetrofitClient
import com.example.madcamp_week2_fe.TAG_HOME
import com.example.madcamp_week2_fe.interfaces.StoreApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DibsFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var storeApi: StoreApiService
    private lateinit var sharedPrefs: SharedPreferences
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dibs, container, false)

        storeApi = RetrofitClient.getInstance().create(StoreApiService::class.java)
        val sharedPrefs = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        var accessToken = sharedPrefs.getString("access_token", null) ?: ""

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val leftArrow: ImageView = view.findViewById(R.id.left_arrow)
        leftArrow.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(TAG_HOME)
        }

        loadStores()

        return view
    }

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