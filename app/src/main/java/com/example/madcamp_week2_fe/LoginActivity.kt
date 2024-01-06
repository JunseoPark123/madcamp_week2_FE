package com.example.madcamp_week2_fe

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnBack: ImageView = findViewById(R.id.left_arrow)
        btnBack.setOnClickListener {
            val intent = Intent(this@LoginActivity, LoginRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val loginButton : Button = findViewById(R.id.next)
        val password : EditText = findViewById(R.id.phone)
        val email : EditText = findViewById(R.id.email)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 텍스트가 변경 되기 전에 호출, 변경 전 텍스트, 변경 시작 위치, 변경 되기 전 길이, 변경 후의 예상 되는 길이.
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 두 EditText에 모두 텍스트가 있는지 확인합니다. 변경 후 텍스트, 변경 시작 위치, 변경 되기 전 길이, 새로 추가되거나 변경된 문자의 수
                loginButton.isEnabled = email.text.trim().isNotEmpty() && password.text.trim().isNotEmpty()

            }
            override fun afterTextChanged(s: Editable?) {
                // 텍스트가 변경된 후에 호출, 변경 후의 텍스트
            }
        }

        // 두 EditText에 TextWatcher를 설정합니다.
        email.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)

        loginButton.setOnClickListener {
            // 로그인 버튼 클릭시 로그인 관련 함수 실행
            val intent = Intent(this@LoginActivity, LocationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}