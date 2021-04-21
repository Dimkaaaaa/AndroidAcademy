package com.example.androidacademy.di

import com.example.androidacademy.data.retrofit.MovieApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object NetWork {

    private val baseUrl = "https://api.themoviedb.org/"
    private val version = "3/"

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val contentType = "application/json".toMediaType()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor())
            .build()

    private val retrofitBuilder = Retrofit.Builder()
            .baseUrl(baseUrl + version)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(httpClient)

    private val retrofit = retrofitBuilder.build()

    val api: MovieApiService by lazy { retrofit.create(MovieApiService::class.java) }
}

class ApiKeyInterceptor: Interceptor {

    companion object {
        private const val API_KEY = "7a37d8b0cf8cd05a218d0eab1f40f4f9"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val urlBuilder = origin.url.newBuilder()
        val url = urlBuilder
                .addQueryParameter("api_key", API_KEY)
                .build()

        val requestBuilder = origin.newBuilder()
                .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}