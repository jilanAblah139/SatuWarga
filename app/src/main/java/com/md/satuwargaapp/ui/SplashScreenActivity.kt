package com.md.satuwargaapp.ui // Sesuaikan package Anda

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.md.satuwargaapp.MainActivity
import com.md.satuwargaapp.R
import com.md.satuwargaapp.data.SessionManager // Import SessionManager Anda
import com.md.satuwargaapp.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private val splashScreenDuration: Long = 2000 // 2 detik

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen) // Pastikan Anda punya layout ini

        sessionManager = SessionManager(this)

        // Gunakan Handler untuk menunda navigasi selama durasi splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            // Cek apakah token ada atau tidak
            if (sessionManager.fetchAuthToken() != null) {
                // Jika ada token (dianggap sudah login), arahkan ke MainActivity
                navigateTo(MainActivity::class.java)
            } else {
                // Jika tidak ada token, arahkan ke LoginActivity
                navigateTo(LoginActivity::class.java)
            }
        }, splashScreenDuration)
    }

    private fun navigateTo(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
        // Panggil finish() agar pengguna tidak bisa kembali ke splash screen dengan tombol "back"
        finish()
    }
}