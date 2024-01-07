package com.example.madcamp_week2_fe.ready

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.madcamp_week2_fe.R

class LoginRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        val btnLogin : Button = findViewById(R.id.next)
        btnLogin.setOnClickListener {
            val intent = Intent(this@LoginRegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        val btnRegister : Button = findViewById(R.id.button_create_account)
        btnRegister.setOnClickListener {
            val intent = Intent(this@LoginRegisterActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}