package com.md.satuwargaapp.ui.papanpengumuman

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.md.satuwargaapp.R
import com.md.satuwargaapp.databinding.FragmentPapanPengumumanBinding

class PapanPengumumanFragment : Fragment() {

    private var _binding: FragmentPapanPengumumanBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<papanPengumuman>()

    private lateinit var adapter: ListPengumumanAdapter
    private lateinit var rvPengumuman: RecyclerView

    companion object {
        fun newInstance() = PapanPengumumanFragment()
    }

    private val viewModel: PapanPengumumanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using binding
        _binding = FragmentPapanPengumumanBinding.inflate(inflater, container, false)
        return binding.root // Return the root view from the binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize RecyclerView
        binding.rvPengumuman.setHasFixedSize(true)
        list.addAll(getListPengumuman())
        showRecyclerList()

        binding.floatingActionButton.setOnClickListener {
            val dialog = DialogPengumumanFragment()
            dialog.listener = object : DialogPengumumanFragment.DialogPengumumanListener {
                override fun onLanjutTambahPostingan() {
                    findNavController().navigate(R.id.action_navigation_papan_pengumuman_to_tambahPostinganFragment)
                }
            }
            dialog.show(parentFragmentManager, "DialogPengumuman")
        }

    }

    private fun getListPengumuman(): ArrayList<papanPengumuman> {
        val dataNama = resources.getStringArray(R.array.data_nama)
        val dataIsi = resources.getStringArray(R.array.data_isi)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataJabatan = resources.getStringArray(R.array.data_jabatan)


        val listPengumuman = ArrayList<papanPengumuman>()
        for (i in dataNama.indices) {
            val pengumuman = papanPengumuman(
                id = i,
                namaUser = dataNama[i],
                isiPengumuman = dataIsi[i],
                photo = dataPhoto.getResourceId(i, -1),
                jabatan = dataJabatan[i],
            )
            listPengumuman.add(pengumuman)
        }
        dataPhoto.recycle() // Always recycle TypedArray after use
        return listPengumuman
    }

    private fun showRecyclerList() {
        // Set up the adapter and layout manager for RecyclerView
        binding.rvPengumuman.layoutManager = LinearLayoutManager(requireContext())
//        val listPengumumanAdapter = ListPengumumanAdapter(list)
        adapter = ListPengumumanAdapter(list,
            onEditClick = { itemToEdit ->
                val bundle = Bundle().apply {
                    putParcelable("EXTRA_EDIT", itemToEdit)
                }
                findNavController().navigate(
                    R.id.action_navigation_papan_pengumuman_to_tambahPostinganFragment,
                    bundle
                )
            }
        )
        binding.rvPengumuman.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear binding to prevent memory leaks
    }
}
