package com.example.madcamp_week2_fe.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.MainActivity
import com.example.madcamp_week2_fe.R

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sampleData = listOf(
            CartItem(R.drawable.image1),
            CartItem(R.drawable.image2),
            CartItem(R.drawable.image3)
        )


        cartAdapter = CartAdapter(sampleData)
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
}