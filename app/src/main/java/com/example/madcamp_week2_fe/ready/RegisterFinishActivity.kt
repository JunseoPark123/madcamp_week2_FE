package com.example.madcamp_week2_fe.ready

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.madcamp_week2_fe.MainActivity
import com.example.madcamp_week2_fe.R

class RegisterFinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_finish)

        val mainButton : Button = findViewById(R.id.next)

        mainButton.setOnClickListener {
            // 로그인 버튼 클릭시 로그인 관련 함수 실행
            val intent = Intent(this@RegisterFinishActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnBack: ImageView = findViewById(R.id.left_arrow)
        btnBack.setOnClickListener {
            val intent = Intent(this@RegisterFinishActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }






}