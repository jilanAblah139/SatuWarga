package com.md.satuwargaapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.md.satuwargaapp.R
import com.md.satuwargaapp.data.SessionManager
import com.md.satuwargaapp.databinding.FragmentHomeBinding
import com.md.satuwargaapp.ui.SplashScreenActivity
import com.md.satuwargaapp.ui.papanpengumuman.ListPengumumanAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // 1. Inisialisasi ViewModel untuk HomeFragment
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var pengumumanAdapter: ListPengumumanAdapter
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext()) // ✅ Inisialisasi session


        setupRecyclerView()
        setupListeners()
        setupObservers()

        // 2. Minta ViewModel untuk mengambil data saat fragment dibuat
        viewModel.loadHomeData()
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
        binding.btnLihatSemuaPengumuman.setOnClickListener {
            findNavController().navigate(R.id.navigation_papan_pengumuman)
        }
        // ✅ Tambahkan aksi logout di sini
        binding.buttonLogout.setOnClickListener {
            sessionManager.clearSession() // hapus token
            Toast.makeText(requireContext(), "Berhasil logout", Toast.LENGTH_SHORT).show()

            // Pindah ke Splash/Login Activity
            val intent = Intent(requireContext(), SplashScreenActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun setupObservers() {
        // Observer untuk data profil pengguna
        viewModel.userProfile.observe(viewLifecycleOwner) { user ->
            // Update UI dengan data pengguna
            binding.tvNama.text = user.nama
            binding.tvAlamat.text = user.alamat

            // Catatan: Model 'User' kita belum punya URL foto profil.
            // Jadi untuk sementara kita pakai placeholder dari drawable.
            Glide.with(this)
                .load(R.drawable.dummyprofile)
                .circleCrop()
                .into(binding.imgProfile)
        }

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