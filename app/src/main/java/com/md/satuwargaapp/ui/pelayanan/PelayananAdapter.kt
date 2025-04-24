package com.md.satuwargaapp.ui.pelayanan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.md.satuwargaapp.R

class PelayananAdapter(private val onItemClickListener: (String) -> Unit) : RecyclerView.Adapter<PelayananAdapter.ViewHolder>() {

    private val pelayananList = listOf(
        Pelayanan("Pelaporan Masalah", "Sampaikan masalah lingkungan dengan cepat.", R.drawable.ic_pelaporan_masalah),
        Pelayanan("Pengajuan Validasi TTD RT RW", "Ajukan validasi untuk dokumen yang membutuhkan tanda tangan RT/RW.", R.drawable.ic_pengajuan_validasi),
        Pelayanan("Pengajuan Surat", "Ajukan berbagai surat resmi seperti domisili atau SKTM.", R.drawable.ic_pengajuan_surat),
        Pelayanan("Pengajuan Izin", "Ajukan izin untuk kegiatan warga atau acara pribadi.", R.drawable.ic_pengajuan_izin),
        Pelayanan("Pengajuan Bantuan Darurat","Minta bantuan darurat untuk situasi mendesak.", R.drawable.ic_bantuan_darurat)
    )

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pelayananName: TextView = view.findViewById(R.id.tv_nama_pel)
        val pelayananDesc: TextView = view.findViewById(R.id.tv_desc_pel)
        val pelayananIcon: ImageView = view.findViewById(R.id.ic_pelayanan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daftar_layanan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pelayanan = pelayananList[position]
        holder.pelayananIcon.setImageResource(pelayanan.imageResId)
        holder.pelayananName.text = pelayanan.name
        holder.pelayananDesc.text = pelayanan.description

        // Set onClickListener untuk setiap item
        holder.itemView.setOnClickListener {
            onItemClickListener(pelayanan.name)
        }
    }

    override fun getItemCount(): Int {
        return pelayananList.size
    }
}

data class Pelayanan(
    val name: String,
    val description: String,
    val imageResId: Int
)
