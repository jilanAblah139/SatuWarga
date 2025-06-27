package com.md.satuwargaapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.md.satuwargaapp.data.ApiClient
import com.md.satuwargaapp.data.LoginResponse // Ganti dengan package model Anda
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun doLogin(email: String, pass: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val requestBody = mapOf("email" to email, "password" to pass)
                // Gunakan publicService karena login tidak butuh token
                val response = ApiClient.publicService.login(requestBody)

                if (response.isSuccessful && response.body() != null) {
                    _loginResult.postValue(Result.success(response.body()!!))
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Login Gagal"
                    _loginResult.postValue(Result.failure(Exception(errorMsg)))
                }
            } catch (e: Exception) {
                _loginResult.postValue(Result.failure(e))
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}