package com.md.satuwargaapp.ui.pelayanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.md.satuwargaapp.R
import com.md.satuwargaapp.databinding.FragmentStatusPelayananBinding

class StatusPelayananFragment : Fragment(), OnStatusClickListener {

    private var _binding: FragmentStatusPelayananBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StatusPelayananAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatusPelayananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mockData = listOf(
            StatusPelayanan("Pelaporan Masalah", "Jalan rusak depan rumah", "Selasa, 01 April 2025", "Diproses"),
            StatusPelayanan("Pengajuan Surat", "Surat keterangan domisili", "Rabu, 02 April 2025", "Selesai"),
            StatusPelayanan("Pengajuan Validasi TTD RT RW", "Validasi Surat Keterangan Ubah KTP", "Jumat, 04 April 2025", "Selesai")
        )

        adapter = StatusPelayananAdapter(mockData, this)
        binding.rvStatLayanan.adapter = adapter
        binding.rvStatLayanan.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onStatusClick(status: StatusPelayanan) {
        // Arahkan ke DetailStatusFragment
        findNavController().navigate(R.id.action_statusPelayananFragment_toDetailStatusFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
