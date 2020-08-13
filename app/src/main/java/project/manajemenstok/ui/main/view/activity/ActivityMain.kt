package project.manajemenstok.ui.main.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import project.manajemenstok.R
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.view.fragment.*
import project.manajemenstok.ui.main.viewmodel.ViewModelAuth
import project.manajemenstok.utils.Constants

class ActivityMain : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    private lateinit var authViewModel: ViewModelAuth

    companion object {
        var tempPembelian = Bundle()
        var tempPenjualan = Bundle()
        var tempKategori = ArrayList<Kategori>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        setupViewModel()
        setupObserver()
        authViewModel.fetchAkubyId(auth.currentUser?.uid.toString())

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

    private fun setupViewModel() {
        val is_remote = true
        authViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(this,is_remote)
        ).get(ViewModelAuth::class.java)
    }

    private fun setupObserver() {
        authViewModel.getAkun().observe(this, Observer {
            it.data?.let { akun ->
                Constants.CONSAKUN = akun
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        Constants.USER_ID = currentUser?.uid.toString()
        if (currentUser == null) {
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

}
