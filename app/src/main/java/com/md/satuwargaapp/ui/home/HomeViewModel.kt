package com.md.satuwargaapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.md.satuwargaapp.data.Announcement
import com.md.satuwargaapp.data.ApiClient
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // LiveData untuk menampung daftar pengumuman terbaru
    private val _latestAnnouncements = MutableLiveData<List<Announcement>>()
    val latestAnnouncements: LiveData<List<Announcement>> = _latestAnnouncements

    // LiveData untuk state loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData untuk pesan error
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    /**
     * Mengambil data pengumuman dari server dan hanya mengambil 3 item teratas.
     */
    fun fetchLatestAnnouncements() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiClient.authService.getAnnouncements()
                if (response.isSuccessful) {
                    // Ambil hanya 3 pengumuman teratas untuk ditampilkan di beranda
                    _latestAnnouncements.postValue(response.body()?.take(3))
                } else {
                    _errorMessage.postValue("Gagal memuat pengumuman: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Terjadi kesalahan jaringan: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun onErrorMessageShown() {
        _errorMessage.value = null
    }
}