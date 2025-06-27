package com.md.satuwargaapp.data

import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Endpoint untuk Login
    @POST("auth/login")
    suspend fun login(@Body requestBody: Map<String, String>): Response<LoginResponse>

    // Endpoint untuk mendapatkan profil pengguna (memerlukan token)
    @GET("auth/me")
    suspend fun getMe(@Header("Authorization") token: String): Response<User>

    // Endpoint untuk mendapatkan semua laporan (memerlukan token)
    @GET("reports")
    suspend fun getReports(@Header("Authorization") token: String): Response<List<Report>>

    @GET("announcements")
    suspend fun getAnnouncements(): Response<List<Announcement>>

    @POST("announcements")
    suspend fun createAnnouncement(@Body requestBody: Map<String, String>): Response<Announcement>
    @DELETE("announcements/{id}")
    suspend fun deleteAnnouncement(@Path("id") announcementId: String): Response<Unit>

    @POST("reports")
    suspend fun createReport(@Body requestBody: Map<String, String>): Response<Report>
}