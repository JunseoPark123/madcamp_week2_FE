package com.example.madcamp_week2_fe.home

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.example.madcamp_week2_fe.ready.LoginRegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var cartAdapter: CartAdapter? = null
    private lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // SharedPreferences에서 accessToken을 불러옵니다.
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        accessToken = sharedPreferences.getString("access_token", "") ?: ""
        var phoneNumber = sharedPreferences.getString("phone_number", "") ?: ""

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadCartItems()


        val phoneTextView: TextView = findViewById(R.id.phone)
        phoneTextView.text = phoneNumber

        recyclerView.adapter = cartAdapter

        val btnBack: ImageView = findViewById(R.id.left_arrow)
        btnBack.setOnClickListener {
            val intent = Intent(this@CartActivity, MainActivity::class.java)
            startActivity(intent)
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
                            it.price.toInt(),
                            it.product_name
                        )
                    }
                    withContext(Dispatchers.Main) {
                        Log.d("CartActivity", "Cart items loaded: ${cartItems.size}")
                        cartAdapter = CartAdapter(this@CartActivity, cartItems)
                        recyclerView.adapter = cartAdapter
                        val payButton: Button = findViewById(R.id.paybutton)
                        payButton.text = "${cart.total_price}원 결제하기" // Set the total price to payButton
                    }
                } else {
                    Log.e("CartActivity", "Failed to load cart items: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("CartActivity", "Error loading cart items: ${e.message}")
            }
        }
    }
}