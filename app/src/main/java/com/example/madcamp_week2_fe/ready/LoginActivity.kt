package com.example.madcamp_week2_fe.ready

import android.annotation.SuppressLint
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var userApiService: UserApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userApiService = RetrofitClient.getInstance().create(UserApiService::class.java)

        val btnBack: ImageView = findViewById(R.id.left_arrow)
        val loginButton: Button = findViewById(R.id.next)
        val password: EditText = findViewById(R.id.password)
        val email: EditText = findViewById(R.id.email)

        btnBack.setOnClickListener {
            val intent = Intent(this@LoginActivity, LoginRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 텍스트가 변경 되기 전에 호출, 변경 전 텍스트, 변경 시작 위치, 변경 되기 전 길이, 변경 후의 예상 되는 길이.
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 두 EditText에 모두 텍스트가 있는지 확인합니다. 변경 후 텍스트, 변경 시작 위치, 변경 되기 전 길이, 새로 추가되거나 변경된 문자의 수
                loginButton.isEnabled =
                    email.text.trim().isNotEmpty() && password.text.trim().isNotEmpty()

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
            val loginRequest = LoginRequest(email.text.toString(), password.text.toString())
            loginUser(loginRequest)
        }
    }

    private fun loginUser(loginRequest: LoginRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = userApiService.loginUser(loginRequest)
                if (response.isSuccessful && response.body() != null) {
                    // 로그인 성공
                    val loginResponse = response.body()!!
                    withContext(Dispatchers.Main) {
                        Log.d("LoginActivity", "Access Token: ${loginResponse.access_token}")
                        val intent = Intent(this@LoginActivity,  MainActivity::class.java).apply {
                        // 필요한 경우 loginResponse의 데이터를 Intent에 추가
                            putExtra("access_token", loginResponse.access_token)
                            putExtra("refresh_token", loginResponse.refresh_token)
                            putExtra("email", loginResponse.email)
                            putExtra("username", loginResponse.username)
                            putExtra("phone_number", loginResponse.phone_number)
                            putExtra("current_location", loginResponse.current_location)
                    }
                        startActivity(intent)
                        finish()
                    }
                } else {
                    // 로그인 실패
                    withContext(Dispatchers.Main) {
                        // 적절한 사용자 피드백 구현
                        Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                // 네트워크 오류 또는 기타 예외 처리
                withContext(Dispatchers.Main) {
                    // 적절한 사용자 피드백 구현
                    Toast.makeText(this@LoginActivity, "로그인 실패: 네트워크 오류", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}