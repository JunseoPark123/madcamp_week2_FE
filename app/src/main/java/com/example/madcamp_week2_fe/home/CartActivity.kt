package com.example.madcamp_week2_fe.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.madcamp_week2_fe.MainActivity
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.RegisterActivity

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

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