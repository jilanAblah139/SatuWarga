package com.md.satuwargaapp.ui.papanpengumuman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.md.satuwargaapp.R
import com.md.satuwargaapp.databinding.FragmentTambahPostinganBinding

class TambahPostinganFragment : Fragment() {

    private var _binding: FragmentTambahPostinganBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTambahPostinganBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnPost.setOnClickListener {
            val isiPostingan = binding.etPostContent.text.toString().trim()

            if (isiPostingan.isEmpty()) {
                Toast.makeText(requireContext(), "Isi postingan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                // Simpan ke database / kirim ke server (kalau sudah ada)
                Toast.makeText(requireContext(), "Postingan berhasil dibuat", Toast.LENGTH_SHORT).show()
                // Kembali ke halaman Pengumuman (dengan asumsi pakai Navigation Component)
                findNavController().navigate(R.id.action_tambahPostinganFragment_to_pengumumanFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}