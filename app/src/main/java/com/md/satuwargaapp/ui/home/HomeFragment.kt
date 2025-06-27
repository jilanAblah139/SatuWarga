package com.md.satuwargaapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.md.satuwargaapp.R
import com.md.satuwargaapp.databinding.FragmentHomeBinding
import com.md.satuwargaapp.ui.papanpengumuman.ListPengumumanAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // 1. Inisialisasi ViewModel untuk HomeFragment
    private val viewModel: HomeViewModel by viewModels()
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

        setupRecyclerView()
        setupListeners()
        setupObservers()

        // 2. Minta ViewModel untuk mengambil data saat fragment dibuat
        viewModel.fetchLatestAnnouncements()
    }

    private fun setupRecyclerView() {
        // 3. Inisialisasi adapter dengan aksi klik yang sesuai
        pengumumanAdapter = ListPengumumanAdapter(
            onEditClick = { announcement ->
                // Aksi untuk edit bisa ditambahkan nanti
                Toast.makeText(context, "Edit: ${announcement.title}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { announcement ->
                // Aksi untuk delete bisa ditambahkan nanti
                Toast.makeText(context, "Hapus: ${announcement.title}", Toast.LENGTH_SHORT).show()
            }
        )

        binding.recyclerPengumuman.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pengumumanAdapter
        }
    }

    private fun setupListeners() {
        // 4. Arahkan ke halaman daftar pengumuman lengkap
        binding.btnLihatSemuaPengumuman.setOnClickListener {
            // Gunakan NavController untuk pindah ke PapanPengumumanFragment
            // Pastikan ID ini sesuai dengan yang ada di navigation graph Anda
            findNavController().navigate(R.id.navigation_papan_pengumuman)
        }
    }

    private fun setupObservers() {
        // 5. Observer untuk daftar pengumuman terbaru
        viewModel.latestAnnouncements.observe(viewLifecycleOwner) { announcementList ->
            // Update data di adapter menggunakan submitList
            pengumumanAdapter.submitList(announcementList)
        }

        // Observer untuk pesan error
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                viewModel.onErrorMessageShown()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}