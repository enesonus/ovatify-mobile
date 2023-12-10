/*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient2 {
    private const val BASE_URL = "https://ovatify-backend-dev.fly.dev/"

    private val httpClient = OkHttpClient.Builder()

    init {
        val authInterceptor = Interceptor { chain ->
            val authToken = TokenManager.getCurrentUserToken() // Use TokenManager to get the token
            val request = chain.request().newBuilder()
                .header("Authorization", "Bearer $authToken")
                .build()
            chain.proceed(request)
        }

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

 */


