package com.md.satuwargaapp.ui.papanpengumuman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.md.satuwargaapp.R

class ListPengumumanAdapter(private val listPengumuman: ArrayList<papanPengumuman>) : RecyclerView.Adapter<ListPengumumanAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaUser = itemView.findViewById<TextView>(R.id.tv_item_name)
        val isiPengumuman = itemView.findViewById<TextView>(R.id.tv_item_isiPengumuman)
        val photo = itemView.findViewById<ImageView>(R.id.img_item_photo)
        val jabatan = itemView.findViewById<TextView>(R.id.tv_item_jabatan)
        val jumlahLikes = itemView.findViewById<TextView>(R.id.jumlahLikes)
        val jumlahComment = itemView.findViewById<TextView>(R.id.jumlahComment)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_pengumuman, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (namaUser, isiPengumuman, photo, jabatan, jumlahLikes, jumlahComment) = listPengumuman[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.photo)
        holder.namaUser.text = namaUser
        holder.isiPengumuman.text = isiPengumuman
        holder.jabatan.text = jabatan
        holder.jumlahLikes.text = jumlahLikes.toString()
        holder.jumlahComment.text = jumlahComment.toString()

    }

    override fun getItemCount(): Int = listPengumuman.size
}