package com.example.madcamp_week2_fe

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

    object RetrofitClient {
        private var instance: Retrofit? = null
        private const val CONNECT_TIMEOUT_SEC = 20000L

        fun getInstance() : Retrofit {
            if(instance == null){

                // 로깅인터셉터 세팅
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

                // OKHttpClient에 로깅인터셉터 등록
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
                    .build()

                instance = Retrofit.Builder()
                    .baseUrl("http://ec2-3-34-151-36.ap-northeast-2.compute.amazonaws.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client) // Retrofit 객체에 OkHttpClient 적용
                    .build()
            }
            return instance!!
        }
    }