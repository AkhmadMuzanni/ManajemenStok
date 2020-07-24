package project.manajemenstok.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.jetbrains.anko.toast
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.main.adapter.MainAdapter
import project.manajemenstok.ui.main.fragment.*
import project.manajemenstok.ui.main.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(){
    companion object {
        var tempPenjual = Bundle()
        fun setTempData(data: Bundle){
            tempPenjual = data
        }

        fun getTempData(): Bundle{
            return tempPenjual
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.database.setPersistenceEnabled(true)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_fragment, FragmentBeranda())
            commit()
        }

        val navigation: BottomNavigationView = findViewById(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){

                R.id.navigation_beranda -> {
                    supportFragmentManager
                        .beginTransaction()
                        .apply {
                            replace(R.id.fl_fragment, FragmentBeranda())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()
                        }
                }

                R.id.navigation_barang -> {
                    supportFragmentManager
                        .beginTransaction()
                        .apply {
                            replace(R.id.fl_fragment, FragmentBarang())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()
                        }
                }

                R.id.navigation_riwayat -> {
                    supportFragmentManager
                        .beginTransaction()
                        .apply {
                            replace(R.id.fl_fragment, FragmentRiwayat())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()
                        }
                }

                R.id.navigation_kas -> {
                    supportFragmentManager
                        .beginTransaction()
                        .apply {
                            replace(R.id.fl_fragment, FragmentKas())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()
                        }
                }

                R.id.navigation_akun -> {
                    supportFragmentManager
                        .beginTransaction()
                        .apply {
                            replace(R.id.fl_fragment, FragmentAkun())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                        }
                }

            }
            true
        }
    }

}
