package com.md.satuwargaapp.ui.papanpengumuman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.md.satuwargaapp.R
import com.md.satuwargaapp.data.Announcement
import com.md.satuwargaapp.data.SessionManager
import com.md.satuwargaapp.databinding.FragmentPapanPengumumanBinding

class PapanPengumumanFragment : Fragment() {

    private var _binding: FragmentPapanPengumumanBinding? = null
    private val binding get() = _binding!!

    // Gunakan activityViewModels() agar ViewModel dapat di-share dengan fragment lain
    private val viewModel: PapanPengumumanViewModel by activityViewModels()

    private lateinit var listAdapter: ListPengumumanAdapter
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPapanPengumumanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        setupRecyclerView()
        setupListeners()
        setupObservers()
        checkUserRole()

        // Panggil data untuk pertama kali saat tampilan dibuat
        // Tampilkan loading indicator dari SwipeRefreshLayout
        binding.swipeRefreshLayout.isRefreshing = true
        viewModel.fetchAnnouncements()
    }

    private fun setupRecyclerView() {
        // Inisialisasi adapter dengan memberikan aksi untuk setiap klik
        listAdapter = ListPengumumanAdapter(

            // Aksi saat tombol "Edit" di-klik
            onEditClick = { announcement ->
                // Membuat bundle untuk mengirim data pengumuman yang akan diedit
                // ke TambahPostinganFragment.
                val bundle = Bundle().apply {
                    // 'announcement' harus dijadikan Parcelable agar bisa dikirim
                    putParcelable("EXTRA_EDIT_ANNOUNCEMENT", announcement)
                }
                findNavController().navigate(
                    R.id.action_navigation_papan_pengumuman_to_tambahPostinganFragment,
                    bundle
                )
            },

            // Aksi saat tombol "Hapus" di-klik
            onDeleteClick = { announcement ->
                // Tampilkan dialog konfirmasi sebelum menghapus
                showDeleteConfirmationDialog(announcement)
            }
        )

        binding.rvPengumuman.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    private fun showDeleteConfirmationDialog(announcement: Announcement) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Pengumuman")
            .setMessage("Apakah Anda yakin ingin menghapus pengumuman berjudul '${announcement.title}'?")
            .setPositiveButton("Hapus") { _, _ ->
                // Jika pengguna menekan "Hapus", panggil fungsi di ViewModel
                viewModel.deleteAnnouncement(announcement.id)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun setupListeners() {
        // Listener untuk pull-to-refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchAnnouncements()
        }

        // Listener untuk tombol tambah
        binding.floatingActionButton.setOnClickListener {
            // Langsung navigasi ke halaman tambah postingan
            findNavController().navigate(R.id.action_navigation_papan_pengumuman_to_tambahPostinganFragment)
        }
    }

    private fun setupObservers() {
        // 1. Observer untuk daftar pengumuman dari ViewModel
        viewModel.announcements.observe(viewLifecycleOwner) { announcementList ->
            // Kirim list baru ke adapter untuk ditampilkan
            listAdapter.submitList(announcementList)
        }

        // 2. Observer untuk status loading
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Atur status refreshing pada SwipeRefreshLayout
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        // 3. Observer untuk pesan toast (error atau sukses)
        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                // Reset pesan agar tidak muncul lagi saat konfigurasi berubah (misal: rotasi)
                viewModel.onToastShown()
            }
        }
    }

    private fun checkUserRole() {
        // Ambil peran pengguna saat ini dari SessionManager
        // Asumsi ada fungsi fetchUserRole() yang mengembalikan "warga", "rt", dll.
        val userRole = sessionManager.fetchUserRole()

        // Tampilkan tombol tambah hanya jika peran adalah rt, rw, atau admin
        if (userRole in listOf("rt", "rw", "admin")) {
            binding.floatingActionButton.visibility = View.VISIBLE
        } else {
            binding.floatingActionButton.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Mencegah memory leak
    }
}