package com.md.satuwargaapp.ui.keuangan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.md.satuwargaapp.R
import com.md.satuwargaapp.databinding.FragmentKeuanganBinding

class KeuanganFragment : Fragment(R.layout.fragment_keuangan) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ensure that the activity is an instance of AppCompatActivity
        val activity = requireActivity() as? AppCompatActivity
        activity?.let {
            // Set up the ViewPager2
            val sectionsPagerAdapter = KeuanganPagerAdapter(it) // Pass AppCompatActivity
            val viewPager: ViewPager2 = view.findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter

            // Set up the TabLayout and ViewPager2 connection
            val tabs: TabLayout = view.findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }
}
