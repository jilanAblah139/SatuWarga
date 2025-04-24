package com.md.satuwargaapp.ui.papanpengumuman

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.md.satuwargaapp.R

class PapanPengumumanFragment : Fragment() {

    companion object {
        fun newInstance() = PapanPengumumanFragment()
    }

    private val viewModel: PapanPengumumanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_papan_pengumuman, container, false)
    }
}