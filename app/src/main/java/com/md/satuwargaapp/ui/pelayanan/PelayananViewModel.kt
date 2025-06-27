package com.md.satuwargaapp.ui.pelayanan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.md.satuwargaapp.data.ApiClient
import com.md.satuwargaapp.data.Report
import kotlinx.coroutines.launch

class PelayananViewModel : ViewModel() {

    // LiveData untuk state loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData untuk hasil pembuatan laporan
    private val _createReportResult = MutableLiveData<Result<Report>>()
    val createReportResult: LiveData<Result<Report>> = _createReportResult

    /**
     * Mengirim data laporan masalah baru ke server.
     */
    fun createReport(judul: String, deskripsi: String, kategori: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Siapkan body request sesuai yang dibutuhkan backend
                val requestBody = mapOf(
                    "judul" to judul,
                    "deskripsi" to deskripsi,
                    "kategori" to kategori
                )

                val response = ApiClient.authService.createReport(requestBody)

                if (response.isSuccessful && response.body() != null) {
                    // Kirim hasil sukses beserta data laporan yang dikembalikan server
                    _createReportResult.postValue(Result.success(response.body()!!))
                } else {
                    // Kirim hasil gagal dengan pesan error dari server
                    val errorMsg = response.errorBody()?.string() ?: "Gagal membuat laporan"
                    _createReportResult.postValue(Result.failure(Exception(errorMsg)))
                }
            } catch (e: Exception) {
                // Kirim hasil gagal jika terjadi error jaringan atau lainnya
                _createReportResult.postValue(Result.failure(e))
            } finally {
                _isLoading.value = false
            }
        }
    }
}