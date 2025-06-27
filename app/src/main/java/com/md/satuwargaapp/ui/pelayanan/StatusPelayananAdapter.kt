package com.md.satuwargaapp.ui.pelayanan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.md.satuwargaapp.R
import com.md.satuwargaapp.data.Report
import com.md.satuwargaapp.databinding.ItemStatusLayananBinding // Pastikan nama binding ini sesuai

// 2. Perbarui interface untuk menggunakan Report
interface OnStatusClickListener {
    fun onStatusClick(report: Report)
}

// 3. Ubah superclass menjadi ListAdapter
class StatusPelayananAdapter(
    private val listener: OnStatusClickListener
) : ListAdapter<Report, StatusPelayananAdapter.ViewHolder>(DIFF_CALLBACK) {

    // ViewHolder tetap sama, hanya metode bind yang disesuaikan
    inner class ViewHolder(val binding: ItemStatusLayananBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // 4. Sesuaikan metode bind untuk menerima objek Report
        fun bind(item: Report) {
            // Mengisi view dengan data dari objek Report
            binding.tvJenisLayanan.text = item.kategori
            binding.tvJudulLaporan.text = item.judul
            binding.textView.text = "Diajukan: ${item.createdAt.substring(0, 10)}" // Ambil tanggal saja
            binding.tvStatus.text = item.status.replaceFirstChar { it.uppercase() }

            // Logika untuk warna status tetap sama
            when (item.status.lowercase()) {
                "diproses" -> binding.tvStatus.setBackgroundResource(R.drawable.bg_status_diproses)
                "selesai" -> binding.tvStatus.setBackgroundResource(R.drawable.bg_status_selesai)
                "diajukan" -> binding.tvStatus.setBackgroundResource(R.drawable.bg_status_terkirim)
            }

            // Set listener untuk klik item, sekarang meneruskan objek Report
            binding.root.setOnClickListener {
                listener.onStatusClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStatusLayananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 5. Gunakan getItem(position) untuk mendapatkan objek Report
        val report = getItem(position)
        holder.bind(report)
    }

    // 6. Hapus data class lokal StatusPelayanan dari file ini

    // 7. Tambahkan DiffUtil.ItemCallback
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Report>() {
            override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean {
                return oldItem == newItem
            }
        }
    }
}