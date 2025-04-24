package com.md.satuwargaapp.ui.pelayanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.md.satuwargaapp.R
import com.md.satuwargaapp.databinding.FragmentListPelayananBinding

class ListPelayananFragment: Fragment() {
    private var _binding: FragmentListPelayananBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPelayananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_list_layanan)

        val pelayananAdapter = PelayananAdapter { pelayananName ->
            when (pelayananName) {
                "Pelaporan Masalah" -> {
                    val navController = findNavController()
                    navController.navigate(R.id.action_listPelayananFragment_to_PelaporanMasalahFragment)
                }
                "Pengajuan Validasi TTD RT RW" -> {
                    val navController = findNavController()
                    navController.navigate(R.id.action_listPelayananFragment_to_PengajuanValidasiFragment)
                }
                "Pengajuan Surat" -> {
                    val navController = findNavController()
                    navController.navigate(R.id.action_listPelayananFragment_to_PengajuanSuratFragment)
                }
                "Pengajuan Izin" -> {
                    val navController = findNavController()
                    navController.navigate(R.id.action_listPelayananFragment_to_PengajuanIzinFragment)
                }
                "Pengajuan Bantuan Darurat" -> {
                    val navController = findNavController()
                    navController.navigate(R.id.action_listPelayananFragment_to_BantuanDaruratFragment)
                }
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = pelayananAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}