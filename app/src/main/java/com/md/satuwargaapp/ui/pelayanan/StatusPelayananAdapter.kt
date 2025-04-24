package com.md.satuwargaapp.ui.pelayanan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.md.satuwargaapp.R
import com.md.satuwargaapp.databinding.ItemStatusLayananBinding

class StatusPelayananAdapter(
    private val list: List<StatusPelayanan>,
    private val listener: OnStatusClickListener
) : RecyclerView.Adapter<StatusPelayananAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemStatusLayananBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StatusPelayanan) {
            binding.tvJenisLayanan.text = item.jenisLayanan
            binding.tvJudulLaporan.text = item.judulLaporan
            binding.textView.text = item.tanggal
            binding.tvStatus.text = item.status

            when (item.status) {
                "Diproses" -> binding.tvStatus.setBackgroundResource(R.drawable.bg_status_diproses)
                "Selesai" -> binding.tvStatus.setBackgroundResource(R.drawable.bg_status_selesai)
                "Terkirim" -> binding.tvStatus.setBackgroundResource(R.drawable.bg_status_terkirim)
            }

            // Set listener untuk klik item
            binding.root.setOnClickListener {
                listener.onStatusClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStatusLayananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

data class StatusPelayanan(
    val jenisLayanan: String,
    val judulLaporan: String,
    val tanggal: String,
    val status: String
)

// Tambahkan interface listener
interface OnStatusClickListener {
    fun onStatusClick(status: StatusPelayanan)
}
