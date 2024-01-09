package com.example.madcamp_week2_fe.home

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.madcamp_week2_fe.MainActivity
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.RetrofitClient
import com.example.madcamp_week2_fe.interfaces.CartApiService
import com.example.madcamp_week2_fe.models.AddToCartRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemInfoActivity : AppCompatActivity() {
    private lateinit var sharedPrefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_info)

        sharedPrefs = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val accessToken = sharedPrefs.getString("access_token", null)
        val storeName = intent.getStringExtra("store")

        val itemImageView: ImageView = findViewById(R.id.itemImage)
        val itemName1TextView: TextView = findViewById(R.id.item)
        val itemName2TextView: TextView = findViewById(R.id.item2)
        val itemPriceTextView: TextView = findViewById(R.id.price)
        val detailName1TextView: TextView = findViewById(R.id.detailName1)
        val detailGram1TextView: TextView = findViewById(R.id.detailGram1)
        val detailName2TextView: TextView = findViewById(R.id.detailName2)
        val detailGram2TextView: TextView = findViewById(R.id.detailGram2)
        val detailName3TextView: TextView = findViewById(R.id.detailName3)
        val detailGram3TextView: TextView = findViewById(R.id.detailGram3)
        val storeTextView: TextView = findViewById(R.id.store)

        itemImageView.setImageResource(R.drawable.image1)

        intent?.let { it ->
            itemName1TextView.text = it.getStringExtra("menuName")
            itemName2TextView.text = it.getStringExtra("menuName")
            itemPriceTextView.text = "${it.getIntExtra("price", 0)}원"
            detailName1TextView.text = it.getStringExtra("detailName1")
            detailGram1TextView.text = it.getStringExtra("detailGram1")
            detailName2TextView.text = it.getStringExtra("detailName2")
            detailGram2TextView.text = it.getStringExtra("detailGram2")
            detailName3TextView.text = it.getStringExtra("detailName3")
            detailGram3TextView.text = it.getStringExtra("detailGram3")
            storeTextView.text = it.getStringExtra("store")
        }

        val btnHeart: ImageView = findViewById(R.id.heart)
        btnHeart.setOnClickListener {
            if (storeName != null && accessToken != null) {
                toggleFavorite(storeName, accessToken)
            } else {
                Toast.makeText(this, "오류: 가게 이름 또는 접근 토큰이 없습니다", Toast.LENGTH_SHORT).show()
            }
        }

        val btnBack: ImageView = findViewById(R.id.left_arrow)
        btnBack.setOnClickListener {
            val intent = Intent(this@ItemInfoActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val productName = intent.getStringExtra("menuName") ?: ""
        val productPrice = intent.getIntExtra("price", 0)

        val btnCart: Button = findViewById(R.id.cart)
        btnCart.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val cartApi = RetrofitClient.getInstance().create(CartApiService::class.java)
                    val response = cartApi.addToCart(
                        "Bearer $accessToken",
                        AddToCartRequest(productName, storeName, productPrice)
                    )

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            // 성공 메시지 표시 및 다이얼로그 표시
                            Toast.makeText(this@ItemInfoActivity, "장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                            showCartDialog()
                        } else {
                            // 실패 메시지 표시
                            Toast.makeText(this@ItemInfoActivity, "추가 실패: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        // 오류 메시지 표시
                        Toast.makeText(this@ItemInfoActivity, "오류가 발생했습니다: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun toggleFavorite(storeName: String, accessToken: String)  {
        val currentStatus = getFavoriteStatus(storeName)
        val newStatus = if (currentStatus == 0) 1 else 0
        saveFavoriteStatus(storeName, newStatus)

        // 업데이트된 즐겨찾기 상태에 따라 아이콘 변경
        findViewById<ImageView>(R.id.heart).setImageResource(
            if (newStatus == 1) R.drawable.redheart
            else R.drawable.heart
        )
    }

    private fun getFavoriteStatus(storeName: String): Int {
        val accessToken = sharedPrefs.getString("access_token", null)
        return sharedPrefs.getInt("$accessToken-$storeName", 0)
    }

    private fun saveFavoriteStatus(storeName: String, status: Int) {
        val accessToken = sharedPrefs.getString("access_token", null)
        sharedPrefs.edit().putInt("$accessToken-$storeName", status).apply()
    }



    private fun showCartDialog() {
        AlertDialog.Builder(this)
            .setTitle("장바구니 추가")
            .setMessage("장바구니에 추가되었습니다. 장바구니로 이동하시겠습니까?")
            .setPositiveButton("예") { dialog, which ->
                startActivity(Intent(this, CartActivity::class.java))
            }
            .setNegativeButton("아니요") { dialog, which ->
                startActivity(Intent(this, MainActivity::class.java))
            }
            .show()
    }

}