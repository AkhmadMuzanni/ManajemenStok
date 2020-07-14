package project.manajemenstok.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import kotlinx.android.synthetic.main.activity_main.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.MainAdapter
import project.manajemenstok.ui.main.viewmodel.MainViewModel
import project.manajemenstok.utils.Status

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()

        val menuPembelian: ConstraintLayout = findViewById(R.id.menu_pembelian)
        menuPembelian.setOnClickListener(this)

//        val navigationBarang: BottomNavigationItemView = findViewById(R.id.navigation_barang)
//        navigationBarang.setOnClickListener(this)

    }

    private fun setupUI(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        val is_remote = true
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(applicationContext,is_remote)
        ).get(MainViewModel::class.java)
    }

    private fun setupObserver() {
        mainViewModel.getBarangs().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
//                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
//                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
//                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(barangs: List<Barang>) {
        adapter.addData(barangs)
        adapter.notifyDataSetChanged()
    }


    override fun onClick(v: View) {
        when (v.id){
            R.id.menu_pembelian->{
//                Toast.makeText(applicationContext, "Ini adalah contoh Toast di Android", Toast.LENGTH_LONG).show()
                val pembelianIntent = Intent(this@MainActivity, PembelianActivity::class.java)
                startActivity(pembelianIntent)
            }
        }
    }


}
