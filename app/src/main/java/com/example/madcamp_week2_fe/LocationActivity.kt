package com.example.madcamp_week2_fe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class LocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)


        val btnBack: ImageView = findViewById(R.id.left_arrow)
        btnBack.setOnClickListener {
            val source = intent.getStringExtra("source")
            if (source == "RegisterActivity") {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }


        val toRegisterFinish : Button = findViewById(R.id.next)
        toRegisterFinish.setOnClickListener {
            val source = intent.getStringExtra("source")
            if (source == "RegisterActivity") {
                startActivity(Intent(this, RegisterFinishActivity::class.java))
            }
            else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }

    }

}