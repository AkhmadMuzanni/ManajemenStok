package project.manajemenstok.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_detail_riwayat.*
import kotlinx.android.synthetic.main.fragment_riwayat.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.DetailRiwayatAdapter
import project.manajemenstok.ui.main.adapter.MainAdapter
import project.manajemenstok.ui.main.adapter.RiwayatAdapter
import project.manajemenstok.ui.main.viewmodel.MainViewModel
import project.manajemenstok.utils.Status

class DetailRiwayatActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: DetailRiwayatAdapter
    private var temp: Int = 0
//    private lateinit var viewFragmentRiwayat: View
//    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_riwayat)
        setupUI()
        setupViewModel()
        setupObserver()
        tv_detail_riwayat_total_price.text =  "Rp. "+temp

    }

    private fun setupUI(){
        rv_detail_riwayat.layoutManager = LinearLayoutManager(this)
        adapter = DetailRiwayatAdapter(arrayListOf())
        rv_detail_riwayat.addItemDecoration(
            DividerItemDecoration(
                rv_detail_riwayat.context,
                (rv_detail_riwayat.layoutManager as LinearLayoutManager).orientation
            )
        )
        rv_detail_riwayat.adapter = adapter
    }

    private fun setupViewModel() {
        val is_remote = true
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(this,is_remote)
        ).get(MainViewModel::class.java)
    }

    private fun setupObserver() {
        mainViewModel.getBarangs().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBarDetailRiwayat.visibility = View.GONE
                    it.data?.let {
                            users -> renderList(users)
                    }
                    it.data?.let { it1 -> getTotal(it1) }
                    rv_detail_riwayat.visibility = View.VISIBLE

                }
                Status.LOADING -> {
                    progressBarDetailRiwayat.visibility = View.VISIBLE
                    rv_detail_riwayat.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBarDetailRiwayat.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(barangs: List<Barang>) {
        adapter.addData(barangs)
        adapter.notifyDataSetChanged()
    }

    private fun getTotal(barangs: List<Barang>){
//        mainViewModel.getBarangs().observe(this, Observer {
//            users->barangs
//        })
        var aa: Int = 0
        for (barang in barangs){
            aa += barang.harga
        }
//        return temp
         temp = 100
    }
}
