package com.md.satuwargaapp.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Context
import okhttp3.Interceptor


object ApiClient {

    // ganti dengan BASE_URL pada lokal, ke terminal > ipconfig > IPv4 address
    private const val BASE_URL = "http://192.168.1.14:5000/api/"

    // Inisiialisasi SessionManager harus dilakukan sekali, idealnya dari Application class.
    // Untuk kesederhanaan, kita buat fungsi init.
    private lateinit var sessionManager: SessionManager

    fun init(context: Context) {
        sessionManager = SessionManager(context)
    }

    // Interceptor untuk logging. Berguna untuk debugging.
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // --- KLIEN UNTUK ENDPOINT PUBLIK ---
    // Klien ini hanya menggunakan logging, tanpa header otorisasi.
    private val publicOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()


    // --- KLIEN UNTUK ENDPOINT TEROTENTIKASI ---
    // Interceptor untuk menambahkan token JWT secara otomatis ke header.
    private val authInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        sessionManager.fetchAuthToken()?.let { token ->
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        chain.proceed(requestBuilder.build())
    }

    // Klien ini menggunakan logging DAN interceptor otorisasi.
    private val authOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()


    // --- INSTANCE RETROFIT UNTUK SETIAP JENIS KLIEN ---

    /**
     * Service untuk endpoint publik (Login, Register).
     * Menggunakan OkHttpClient tanpa token.
     */
    val publicService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(publicOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    /**
     * Service untuk endpoint yang memerlukan otentikasi (getMe, getReports, dll).
     * Menggunakan OkHttpClient yang sudah dilengkapi token secara otomatis.
     */
    val authService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(authOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}