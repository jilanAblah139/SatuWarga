package com.md.satuwargaapp.ui.papanpengumuman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.md.satuwargaapp.R
import com.md.satuwargaapp.data.Announcement

class ListPengumumanAdapter(

    private val onEditClick: (Announcement) -> Unit,
    private val onDeleteClick: (Announcement) -> Unit
) : ListAdapter<Announcement, ListPengumumanAdapter.ListViewHolder>(ANNOUNCEMENT_COMPARATOR) {

    // 3. ViewHolder disesuaikan untuk menerima objek Announcement
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val namaUser: TextView = itemView.findViewById(R.id.tv_item_name)
        private val isiPengumuman: TextView = itemView.findViewById(R.id.tv_item_isiPengumuman)
        private val jabatan: TextView = itemView.findViewById(R.id.tv_item_jabatan)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        private val editButton: Button = itemView.findViewById(R.id.editButton)

        fun bind(announcement: Announcement, onEditClick: (Announcement) -> Unit, onDeleteClick: (Announcement) -> Unit) {
            // Binding data dari objek Announcement ke view
            namaUser.text = announcement.user.nama
            jabatan.text = announcement.user.role.replaceFirstChar { it.uppercase() } // Contoh format role
            isiPengumuman.text = announcement.content

            // Atur listener klik, memanggil lambda yang diberikan
            editButton.setOnClickListener { onEditClick(announcement) }
            deleteButton.setOnClickListener { onDeleteClick(announcement) }

            // Anda juga bisa mengatur visibilitas tombol edit/delete berdasarkan peran pengguna di sini
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_pengumuman, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        // 4. Gunakan getItem(position) untuk mendapatkan objek
        val announcement = getItem(position)
        holder.bind(announcement, onEditClick, onDeleteClick)
    }

    // 5. Buat DiffUtil.ItemCallback untuk perbandingan list yang efisien
    companion object {
        private val ANNOUNCEMENT_COMPARATOR = object : DiffUtil.ItemCallback<Announcement>() {
            override fun areItemsTheSame(oldItem: Announcement, newItem: Announcement): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Announcement, newItem: Announcement): Boolean {
                return oldItem == newItem
            }
        }
    }
}