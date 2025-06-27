package com.md.satuwargaapp.ui.pelayanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.md.satuwargaapp.data.Report
import com.md.satuwargaapp.databinding.FragmentStatusPelayananBinding

class StatusPelayananFragment : Fragment(), OnStatusClickListener {

    private var _binding: FragmentStatusPelayananBinding? = null
    private val binding get() = _binding!!

    // Gunakan ViewModel yang sama dari activity
    private val viewModel: PelayananViewModel by activityViewModels()
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

        setupRecyclerView()
        setupObservers()

        // Minta ViewModel untuk mengambil data laporan
        viewModel.fetchReports()
    }

    private fun setupRecyclerView() {
        // Inisialisasi adapter baru yang sudah disesuaikan
        adapter = StatusPelayananAdapter(this)
        binding.rvStatLayanan.adapter = adapter
        binding.rvStatLayanan.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {
        // Observer untuk daftar laporan dari ViewModel
        viewModel.reportList.observe(viewLifecycleOwner) { reportList ->
            // Kirim daftar baru ke adapter
            adapter.submitList(reportList)
        }

        // Observer untuk status loading
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Anda bisa tambahkan ProgressBar dan mengaturnya di sini
            // binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observer untuk pesan toast
        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStatusClick(report: Report) {
        // Arahkan ke DetailStatusFragment, mungkin dengan membawa ID laporan
        val action = StatusPelayananFragmentDirections.actionStatusPelayananFragmentToDetailStatusFragment(report.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}