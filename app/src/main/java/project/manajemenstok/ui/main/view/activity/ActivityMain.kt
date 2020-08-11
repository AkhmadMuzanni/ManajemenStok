package project.manajemenstok.ui.main.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import project.manajemenstok.R
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.ui.main.view.fragment.*

class ActivityMain : AppCompatActivity(){
    companion object {
        var tempPembelian = Bundle()
        var tempPenjualan = Bundle()
        var tempKategori = ArrayList<Kategori>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_fragment,
                FragmentBeranda()
            )
            commit()
        }

        val navigation: BottomNavigationView = findViewById(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){

                R.id.navigation_beranda -> {
                    supportFragmentManager
                        .beginTransaction()
                        .apply {
                            replace(R.id.fl_fragment,
                                FragmentBeranda()
                            )
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()
                        }
                }

                R.id.navigation_barang -> {
                    supportFragmentManager
                        .beginTransaction()
                        .apply {
                            replace(R.id.fl_fragment,
                                FragmentBarang()
                            )
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()
                        }
                }

                R.id.navigation_riwayat -> {
                    supportFragmentManager
                        .beginTransaction()
                        .apply {
                            replace(R.id.fl_fragment,
                                FragmentRiwayat()
                            )
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()
                        }
                }

                R.id.navigation_kas -> {
                    supportFragmentManager
                        .beginTransaction()
                        .apply {
                            replace(R.id.fl_fragment,
                                FragmentKas()
                            )
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()
                        }
                }

                R.id.navigation_akun -> {
                    supportFragmentManager
                        .beginTransaction()
                        .apply {
                            replace(R.id.fl_fragment,
                                FragmentAkun()
                            )
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                        }
                }

            }
            true
        }
    }

}
