package com.example.madcamp_week2_fe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        val btnLogin : Button = findViewById(R.id.button_login)
        btnLogin.setOnClickListener {
            val intent = Intent(this@LoginRegisterActivity, MainActivity::class.java)
            startActivity(intent)
        }

        val btnRegister : Button = findViewById(R.id.button_create_account)
        btnRegister.setOnClickListener {
            val intent = Intent(this@LoginRegisterActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}