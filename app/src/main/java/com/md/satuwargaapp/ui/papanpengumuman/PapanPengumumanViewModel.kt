package com.md.satuwargaapp.ui.papanpengumuman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.md.satuwargaapp.data.Announcement
import com.md.satuwargaapp.data.ApiClient
import kotlinx.coroutines.launch

class PapanPengumumanViewModel : ViewModel() {

    // LiveData untuk menampung daftar pengumuman
    private val _announcements = MutableLiveData<List<Announcement>>()
    val announcements: LiveData<List<Announcement>> = _announcements

    // LiveData untuk state loading (untuk ProgressBar atau SwipeRefreshLayout)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData untuk mengirim pesan error/sukses singkat ke UI
    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    // LiveData untuk menandakan hasil postingan (sukses atau tidak)
    private val _postResult = MutableLiveData<Boolean>()
    val postResult: LiveData<Boolean> = _postResult


    /**
     * Mengambil data pengumuman dari server.
     */
    fun fetchAnnouncements() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiClient.authService.getAnnouncements()
                if (response.isSuccessful) {
                    _announcements.postValue(response.body())
                } else {
                    _toastMessage.postValue("Gagal memuat data: ${response.message()}")
                }
            } catch (e: Exception) {
                _toastMessage.postValue("Terjadi kesalahan: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    /**
     * Mengirim pengumuman baru ke server.
     */
    fun createAnnouncement(title: String, content: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val requestBody = mapOf("title" to title, "content" to content)
                val response = ApiClient.authService.createAnnouncement(requestBody)
                if (response.isSuccessful) {
                    _toastMessage.postValue("Pengumuman berhasil diposting!")
                    _postResult.postValue(true) // Kirim sinyal sukses
                    fetchAnnouncements() // Muat ulang data agar yang baru muncul
                } else {
                    _toastMessage.postValue("Gagal memposting: ${response.message()}")
                    _postResult.postValue(false) // Kirim sinyal gagal
                }
            } catch (e: Exception) {
                _toastMessage.postValue("Terjadi kesalahan: ${e.message}")
                _postResult.postValue(false)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun deleteAnnouncement(announcementId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiClient.authService.deleteAnnouncement(announcementId)
                if (response.isSuccessful) {
                    _toastMessage.postValue("Pengumuman berhasil dihapus.")
                    // Muat ulang daftar pengumuman untuk memperbarui UI
                    fetchAnnouncements()
                } else {
                    _toastMessage.postValue("Gagal menghapus pengumuman.")
                }
            } catch (e: Exception) {
                _toastMessage.postValue("Terjadi kesalahan: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    // Fungsi untuk mereset toast message agar tidak muncul lagi saat rotasi layar
    fun onToastShown() {
        _toastMessage.value = null
    }
}