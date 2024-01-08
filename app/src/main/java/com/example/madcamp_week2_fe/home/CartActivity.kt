package com.example.madcamp_week2_fe.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.CartAdapter
import com.example.madcamp_week2_fe.MainActivity
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.RetrofitClient
import com.example.madcamp_week2_fe.interfaces.CartApiService
import com.example.madcamp_week2_fe.models.Cart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var cartAdapter: CartAdapter? = null // lateinit 대신 nullable 타입으로 선언
    private lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this) // 레이아웃 매니저 초기화

        accessToken = intent.getStringExtra("access_token") ?: ""

        loadCartItems()




        recyclerView.adapter = cartAdapter

        val btnBack: ImageView = findViewById(R.id.left_arrow)
        btnBack.setOnClickListener {
            val source = intent.getStringExtra("source")
            if (source == "ItemInfoActivity") {
                startActivity(Intent(this, ItemInfoActivity::class.java))
            }
            else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }
    }

    private fun loadCartItems() {
        val cartApi = RetrofitClient.getInstance().create(CartApiService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = cartApi.getCart("Bearer $accessToken")
                if (response.isSuccessful && response.body() != null) {
                    val cart = response.body()!!
                    val cartItems = cart.cart_items.map {
                        CartItem(
                            R.drawable.image1,
                            it.price.toInt(), // Assuming price is compatible with Int
                            it.product_name
                        )
                    }
                    withContext(Dispatchers.Main) {
                        cartAdapter = CartAdapter(this@CartActivity, cartItems)
                        recyclerView.adapter = cartAdapter

                        // Accessing total_price from the Cart object
                        val totalAmountTextView: Button = findViewById(R.id.paybutton)
                        totalAmountTextView.text = "${cart.total_price}원 결제하기"

                        // ... other UI updates
                    }
                } else {
                    // ... handle error case
                }
            } catch (e: Exception) {
                // ... handle exception case
            }
        }
    }
}