package project.manajemenstok.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import project.manajemenstok.ui.main.fragment.FragmentInputBarangBaru
import project.manajemenstok.ui.main.fragment.FragmentInputBarangTersimpan
import project.manajemenstok.ui.main.fragment.FragmentPembelian

class InputBarangAdapter(fm: FragmentManager, private val transaksi: Int) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if(transaksi == 0){
            return when (position) {
                0 -> {
                    FragmentInputBarangBaru()
                }
                else -> {
                    return FragmentInputBarangTersimpan()
                }
            }
        } else {
            return FragmentInputBarangTersimpan()
        }
    }

    override fun getCount(): Int {
        if(transaksi == 0){
            return 2
        } else {
            return 1
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(transaksi == 0){
            return when (position) {
                0 -> "Barang Baru"
                else -> {
                    return "Barang Tersimpan"
                }
            }
        } else {
            return "Barang Tersimpan"
        }
    }
}