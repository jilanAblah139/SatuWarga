package com.md.satuwargaapp.data

import android.os.Parcelable // <-- Pastikan import ini ada
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize // <-- Dan import ini

// 1. Jadikan User Parcelable terlebih dahulu karena akan digunakan di kelas lain.
@Parcelize
data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("alamat")
    val alamat: String
) : Parcelable // <-- Implement Parcelable

// 2. Sekarang Report bisa menjadi Parcelable jika Anda perlu mengirimnya
@Parcelize
data class Report(
    @SerializedName("_id")
    val id: String,
    @SerializedName("judul")
    val judul: String,
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("kategori")
    val kategori: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("user")
    val user: User // Ini bisa bekerja karena User sekarang Parcelable
) : Parcelable

// 3. Kode Announcement Anda sudah benar, sekarang ia akan berfungsi dengan baik.
@Parcelize
data class Announcement(
    @SerializedName("_id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("user")
    val user: User, // Ini bisa bekerja karena User sekarang Parcelable
    @SerializedName("createdAt")
    val createdAt: String
) : Parcelable

// 4. Feedback juga bisa menjadi Parcelable jika perlu
@Parcelize
data class Feedback(
    @SerializedName("_id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("user")
    val user: User, // Ini bisa bekerja karena User sekarang Parcelable
    @SerializedName("createdAt")
    val createdAt: String
) : Parcelable