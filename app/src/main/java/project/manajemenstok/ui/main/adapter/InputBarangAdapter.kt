package project.manajemenstok.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import project.manajemenstok.ui.main.fragment.FragmentInputBarangBaru
import project.manajemenstok.ui.main.fragment.FragmentInputBarangTersimpan
import project.manajemenstok.ui.main.fragment.FragmentPembelian

class InputBarangAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentInputBarangBaru()
            }
            else -> {
                return FragmentInputBarangTersimpan()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Barang Baru"
            else -> {
                return "Barang Tersimpan"
            }
        }
    }
}