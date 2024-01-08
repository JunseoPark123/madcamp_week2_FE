package com.example.madcamp_week2_fe.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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


        val btnCart: Button = findViewById(R.id.cart)
        btnCart.setOnClickListener {
            val intent = Intent(this@ItemInfoActivity, CartActivity::class.java)
            intent.putExtra("source", "ItemInfoActivity")
            startActivity(intent)
        }

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
    }


}