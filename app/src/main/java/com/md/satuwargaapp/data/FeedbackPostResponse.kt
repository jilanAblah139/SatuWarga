package com.md.satuwargaapp.data

import com.google.gson.annotations.SerializedName

/**
 * Merepresentasikan struktur JSON lengkap dari respons
 * saat berhasil membuat feedback baru (POST /feedback).
 */
data class FeedbackPostResponse(

    @SerializedName("msg")
    val message: String,

    // Objek feedback yang baru dibuat akan berada di dalam field 'data'
    @SerializedName("data")
    val data: Feedback
)