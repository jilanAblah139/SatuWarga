package com.md.satuwargaapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.md.satuwargaapp.MainActivity
import com.md.satuwargaapp.data.SessionManager
import com.md.satuwargaapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginViewModel.doLogin(email, password)
        }
    }

    private fun setupObservers() {
        // Observer untuk hasil login (sukses atau gagal)
        loginViewModel.loginResult.observe(this) { result ->
            result.onSuccess { loginResponse ->
                // Jika sukses, simpan token dan pindah ke MainActivity
                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                sessionManager.saveAuthToken(loginResponse.token)
                sessionManager.saveUserRole(loginResponse.user.role)
                navigateToMain()
            }.onFailure { error ->
                // Jika gagal, tampilkan pesan error
                Toast.makeText(this, "Login Gagal: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }

        // Observer untuk status loading (menampilkan/menyembunyikan ProgressBar)
        loginViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        // Flag untuk menghapus semua activity sebelumnya dari back stack
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        // Cek jika token sudah ada (auto-login)
        if (sessionManager.fetchAuthToken() != null) {
            navigateToMain()
        }
    }
}