package com.example.madcamp_week2_fe.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.madcamp_week2_fe.LoginRegisterActivity
import com.example.madcamp_week2_fe.MainActivity
import com.example.madcamp_week2_fe.R

class ItemInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_info)


        val btnBack: ImageView = findViewById(R.id.left_arrow)
        btnBack.setOnClickListener {
            val intent = Intent(this@ItemInfoActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}