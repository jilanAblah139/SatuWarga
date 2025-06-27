package com.md.satuwargaapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.md.satuwargaapp.data.Announcement
import com.md.satuwargaapp.data.ApiClient
import com.md.satuwargaapp.data.User

import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // LiveData untuk data profil pengguna
    private val _userProfile = MutableLiveData<User>()
    val userProfile: LiveData<User> = _userProfile

    // LiveData lain yang sudah ada
    private val _latestAnnouncements = MutableLiveData<List<Announcement>>()
    val latestAnnouncements: LiveData<List<Announcement>> = _latestAnnouncements

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    /**
     * Fungsi terpusat untuk memuat semua data yang dibutuhkan HomeFragment
     */
    fun loadHomeData() {
        fetchUserProfile()
        fetchLatestAnnouncements()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            try {
                val response = ApiClient.authService.getMe()
                if (response.isSuccessful) {
                    _userProfile.postValue(response.body())
                } else {
                    _errorMessage.postValue("Gagal memuat profil pengguna")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error profil: ${e.message}")
            }
        }
    }

    private fun fetchLatestAnnouncements() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiClient.authService.getAnnouncements()
                if (response.isSuccessful) {
                    _latestAnnouncements.postValue(response.body()?.take(3))
                } else {
                    _errorMessage.postValue("Gagal memuat pengumuman")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error pengumuman: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onErrorMessageShown() {
        _errorMessage.value = null
    }
}