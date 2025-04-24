package com.md.satuwargaapp.ui.pelayanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.md.satuwargaapp.R
import com.md.satuwargaapp.databinding.FragmentPelaporanMasalahBinding

class PelaporanMasalahFragment : Fragment() {

    private var _binding: FragmentPelaporanMasalahBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Detail Penyakit"
        }
        //setHasOptionsMenu(true)

        _binding = FragmentPelaporanMasalahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLaporFoto.setOnClickListener{
            val navController = findNavController()
            navController.navigate(R.id.action_PelaporanMasalahFragment_to_laporFotoFragment)
        }
    }
}