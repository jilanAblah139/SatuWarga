package com.md.satuwargaapp.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("SatuWargaAppPrefs", Context.MODE_PRIVATE)

    companion object {
        const val AUTH_TOKEN = "auth_token"
        const val USER_ROLE = "user_role" // Kunci baru untuk peran
    }

    /**
     * Menyimpan token autentikasi.
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(AUTH_TOKEN, token)
        editor.apply()
    }

    /**
     * Menyimpan peran pengguna.
     */
    fun saveUserRole(role: String) {
        val editor = prefs.edit()
        editor.putString(USER_ROLE, role)
        editor.apply()
    }

    /**
     * Mengambil token autentikasi.
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(AUTH_TOKEN, null)
    }

    /**
     * Mengambil peran pengguna.
     * @return null jika peran tidak ada, default ke "warga".
     */
    fun fetchUserRole(): String? {
        return prefs.getString(USER_ROLE, "warga")
    }

    /**
     * Menghapus semua data sesi (untuk logout).
     */
    fun clearSession() {
        val editor = prefs.edit()
        editor.remove(AUTH_TOKEN)
        editor.remove(USER_ROLE)
        editor.apply()
    }
}