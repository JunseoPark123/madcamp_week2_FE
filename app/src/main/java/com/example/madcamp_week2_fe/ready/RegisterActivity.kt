package com.example.madcamp_week2_fe.ready

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.madcamp_week2_fe.MainActivity
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.RetrofitClient
import com.example.madcamp_week2_fe.interfaces.UserApiService
import com.example.madcamp_week2_fe.models.LoginRequest
import com.example.madcamp_week2_fe.models.RegisterRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    private lateinit var userApiService: UserApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnBack: ImageView = findViewById(R.id.left_arrow)
        btnBack.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val registerButton : Button = findViewById(R.id.next)
        val password: EditText = findViewById(R.id.phone)
        val email: EditText = findViewById(R.id.email)
        val name: EditText = findViewById(R.id.name)
        val phone: EditText = findViewById(R.id.phone)
        val location : EditText = findViewById(R.id.location)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 텍스트가 변경 되기 전에 호출, 변경 전 텍스트, 변경 시작 위치, 변경 되기 전 길이, 변경 후의 예상 되는 길이.
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 네 EditText에 모두 텍스트가 있는지 확인합니다. 변경 후 텍스트, 변경 시작 위치, 변경 되기 전 길이, 새로 추가되거나 변경된 문자의 수
                registerButton.isEnabled = email.text.trim().isNotEmpty() && password.text.trim().isNotEmpty() && name.text.trim().isNotEmpty() && phone.text.trim().isNotEmpty() && location.text.trim().isNotEmpty()

            }
            override fun afterTextChanged(s: Editable?) {
                // 텍스트가 변경된 후에 호출, 변경 후의 텍스트
            }
        }

        email.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)
        name.addTextChangedListener(textWatcher)
        phone.addTextChangedListener(textWatcher)
        location.addTextChangedListener(textWatcher)
        userApiService = RetrofitClient.getInstance().create(UserApiService::class.java)

        registerButton.setOnClickListener {
            val registerRequest = RegisterRequest(email.text.toString(), name.text.toString(), password.text.toString(), phone.text.toString(), location.text.toString())
            registerUser(registerRequest)
        }
    }

    private fun registerUser(registerRequest: RegisterRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("RegisterActivity", "userApiService 초기화 여부: ${::userApiService.isInitialized}")
                val response = userApiService.registerUser(registerRequest)
                if (response.isSuccessful && response.body() != null) {
                    // 회원가입 성공
                    val registerResponse = response.body()!!
                    withContext(Dispatchers.Main) {
                        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                        sharedPreferences.edit().apply {
                            val location : EditText = findViewById(R.id.location)
                            putString("location_info", location.text.toString())
                            apply()
                        }
                        Toast.makeText(this@RegisterActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                } else {
                    // 회원가입 실패 - 서버 응답 오류
                    Log.e("RegisterActivity", "회원가입 실패: ${response.errorBody()?.string()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, "회원가입 실패: 서버 오류", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                // 네트워크 오류 또는 기타 예외 처리
                Log.e("RegisterActivity", "회원가입 실패: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity, "회원가입 실패: 네트워크 오류", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}