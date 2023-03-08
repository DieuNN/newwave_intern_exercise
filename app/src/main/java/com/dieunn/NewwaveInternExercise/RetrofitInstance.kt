package com.dieunn.NewwaveInternExercise

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Create retrofit instance with api_key as default query
    val api: MovieAPI = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val url = chain
                        .request()
                        .url
                        .newBuilder()
                        .addQueryParameter("api_key", Constants.API_KEY)
                        .build()
                    println(url.toUrl().toString())
                    chain.proceed(chain.request().newBuilder().url(url).build())
                }
                .build()
        )
        .build()
        .create(MovieAPI::class.java)
}