package com.md.satuwargaapp.ui.papanpengumuman

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class papanPengumuman(
    val namaUser: String,
    val isiPengumuman: String,
    val photo:Int,
    val jabatan: String,
    val jumlahLikes: Int,
    val jumlahComment: Int

):Parcelable
