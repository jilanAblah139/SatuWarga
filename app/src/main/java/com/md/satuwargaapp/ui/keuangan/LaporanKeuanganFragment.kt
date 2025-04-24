package com.md.satuwargaapp.ui.keuangan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.md.satuwargaapp.R

class LaporanKeuanganFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Ganti "R.layout.fragment_laporan_keuangan" dengan nama file layout kamu
        return inflater.inflate(R.layout.fragment_laporan_keuangan, container, false)
    }
}
