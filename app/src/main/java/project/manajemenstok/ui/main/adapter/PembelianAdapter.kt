package project.manajemenstok.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import project.manajemenstok.ui.main.fragment.FragmentPembelian

class PembelianAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentPembelian()
            }
            1 -> FragmentPembelian()
            else -> {
                return FragmentPembelian()
            }
        }
    }

    override fun getCount(): Int {
        return 1
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Pembelian"
            1 -> "Second Tab"
            else -> {
                return "Third Tab"
            }
        }
    }
}