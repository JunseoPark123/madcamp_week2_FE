package com.example.madcamp_week2_fe.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.CartAdapter
import com.example.madcamp_week2_fe.MainActivity
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.RetrofitClient
import com.example.madcamp_week2_fe.interfaces.CartApiService
import com.example.madcamp_week2_fe.models.AddToOrderRequest
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
        val payButton: Button = findViewById(R.id.paybutton)
        payButton.setOnClickListener {
            showPayDialog()
        }
    }

    private fun showPayDialog() {
        AlertDialog.Builder(this)
            .setTitle("결제 확인")
            .setMessage("진짜 결제 하실 건가요?")
            .setPositiveButton("예") { _, _ ->
                processPayment()
            }
            .setNegativeButton("아니요", null)
            .show()
    }

    private fun processPayment() {

        processOrders()


        clearCartItems()
    }

    private fun clearCartItems() {
        // 장바구니 아이템 리스트를 비우고 어댑터를 갱신
        cartAdapter?.let { adapter ->
            adapter.items.clear()
            adapter.notifyDataSetChanged()
        }
    }



    private fun processOrders() {
        cartAdapter?.let { adapter ->
            adapter.items.forEach { item ->
                placeOrder(item)
            }
        }
    }

    private fun placeOrder(item: CartItem) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val addToOrderRequest = AddToOrderRequest(item.product_name, item.store_name ?: "", item.price)
                val response = RetrofitClient.getInstance().create(CartApiService::class.java).addToOrder(
                    "Bearer $accessToken", addToOrderRequest
                )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Log.d("CartActivity", "Order placed successfully for ${item.product_name}")
                        Toast.makeText(this@CartActivity, "주문이 성공적으로 처리되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                        Log.e("CartActivity", "Order failed: $errorMessage")
                        Toast.makeText(this@CartActivity, "주문 실패: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("CartActivity", "Error placing order: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CartActivity, "오류: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
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
                            it.product_name,
                            it.store_name,
                        )
                    }.toMutableList()
                    withContext(Dispatchers.Main) {
                        Log.d("CartActivity", "Cart items loaded: ${cartItems.size}")
                        cartAdapter = CartAdapter(this@CartActivity, cartItems)
                        recyclerView.adapter = cartAdapter
                        val payButton: Button = findViewById(R.id.paybutton)
                        payButton.text = "카카오페이로 ${cart.total_price}원 결제하기" // Set the total price to payButton
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