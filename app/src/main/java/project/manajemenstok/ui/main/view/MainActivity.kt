package project.manajemenstok.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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


        val navigationBeranda: BottomNavigationItemView = findViewById(R.id.navigation_beranda)
        navigationBeranda.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_fragment, FragmentBeranda())
                commit()
            }
        }

        val navigationBarang: BottomNavigationItemView = findViewById(R.id.navigation_barang)
        navigationBarang.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_fragment, FragmentBarang())
                commit()
            }
        }

        val navigationRiwayat: BottomNavigationItemView = findViewById(R.id.navigation_riwayat)
        navigationRiwayat.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_fragment, FragmentRiwayat())
                commit()
            }
        }

        val navigationKas: BottomNavigationItemView = findViewById(R.id.navigation_kas)
        navigationKas.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_fragment, FragmentKas())
                commit()
            }
        }

        val navigationAkun: BottomNavigationItemView = findViewById(R.id.navigation_akun)
        navigationAkun.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_fragment, FragmentAkun())
                commit()
            }
        }
    }

}
