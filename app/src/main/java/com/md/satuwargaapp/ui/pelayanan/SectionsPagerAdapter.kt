package com.md.satuwargaapp.ui.pelayanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter (fragment: Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    private var arguments: Bundle? = null

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 ->{
                fragment = ListPelayananFragment()
                fragment.arguments = arguments
            }
            1 -> {
                fragment = StatusPelayananFragment()
                fragment.arguments = arguments
            }
        }
        return fragment as Fragment
    }
}