package com.md.satuwargaapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.md.satuwargaapp.R
import com.md.satuwargaapp.databinding.FragmentHomeBinding
import com.md.satuwargaapp.ui.papanpengumuman.ListPengumumanAdapter
import com.md.satuwargaapp.ui.papanpengumuman.papanPengumuman

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var pengumumanAdapter: ListPengumumanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPengumuman()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnLihatSemuaPengumuman.setOnClickListener {
            Toast.makeText(requireContext(), "Lihat semua pengumuman", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupPengumuman() {
        val dummyList = arrayListOf(
            papanPengumuman(
                id = 1,
                namaUser = "Dadang Sutmajaya",
                isiPengumuman = "Bapak, ibu, mohon maaf tukang sampah hari ini tidak bisa mengangkut, ban nya bocor.",
                photo = R.drawable.dummyprofile,
                jabatan = "Ketua RT"
            ),
            papanPengumuman(
                id = 2,
                namaUser = "Siti Rohmah",
                isiPengumuman = "Besok akan ada kerja bakti jam 7:30 WIB, harap partisipasinya.",
                photo = R.drawable.dummyprofile,
                jabatan = "Sekretaris RW"
            )
        )

        pengumumanAdapter = ListPengumumanAdapter(dummyList) { selectedItem ->
            Toast.makeText(requireContext(), "Klik edit: ${selectedItem.namaUser}", Toast.LENGTH_SHORT).show()
            // Tambahkan navigasi ke form edit jika ada
        }

        binding.recyclerPengumuman.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pengumumanAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
