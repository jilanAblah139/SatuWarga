package com.md.satuwargaapp.ui.pelayanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.md.satuwargaapp.R
import com.md.satuwargaapp.databinding.FragmentLaporFotoBinding // Ganti dengan nama binding Anda

class LaporFotoFragment : Fragment() {

    private var _binding: FragmentLaporFotoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PelayananViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Mengatur judul di ActionBar
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Pelaporan Masalah"
        _binding = FragmentLaporFotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {

        // Listener untuk tombol KIRIM LAPORAN
        binding.btnKirimLaporan.setOnClickListener {
            val judul = binding.edJudulLaporan.text.toString().trim()
            val deskripsi = binding.edDetailLaporan.text.toString().trim()
            val kategori = "Infrastruktur" // Contoh, Anda bisa ganti dengan input lain

            if (judul.isEmpty() || deskripsi.isEmpty()) {
                Toast.makeText(context, "Judul dan Detail Masalah wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Panggil ViewModel untuk mengirim data TEKS
            viewModel.createReport(judul, deskripsi, kategori)
        }
    }

    private fun setupObservers() {

        viewModel.createReportResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                Toast.makeText(context, "Laporan berhasil terkirim (tanpa foto)", Toast.LENGTH_LONG).show()
                // Kembali ke halaman daftar layanan setelah sukses
                findNavController().popBackStack(R.id.navigation_pelayanan, false)
            }.onFailure {
                Toast.makeText(context, "Gagal mengirim laporan: ${it.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}