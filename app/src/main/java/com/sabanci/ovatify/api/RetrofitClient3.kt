package com.sabanci.ovatify.api

import ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient3(val authToken : String) {
    //private var retrofit: Retrofit? = null

    private val BASE_URL = "https://ovatify-backend-dev.fly.dev/"
    //private const val BASE_URL = "https://34fe-159-20-69-22.ngrok-free.app"
    //public var AUTH_TOKEN = "e5e28a48-8080-11ee-b962-0242ac120002"
    var AUTH_TOKEN = authToken
    //"e5e28a48-8080-11ee-b962-0242ac120002"

    private val httpClient = OkHttpClient.Builder()


    init {
        // Add an interceptor to add the Bearer token to the Authorization header



        val authInterceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Authorization", "Bearer $AUTH_TOKEN")
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }



        // Add the interceptor to the OkHttpClient
        httpClient.addInterceptor(authInterceptor)


    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}