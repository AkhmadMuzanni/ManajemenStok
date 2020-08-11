package project.manajemenstok.ui.main.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_riwayat.*
import project.manajemenstok.R
import project.manajemenstok.data.model.DetailTransaksiData
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.DetailRiwayatAdapter
import project.manajemenstok.ui.main.viewmodel.ViewModelRiwayat
import project.manajemenstok.utils.Status
import java.text.NumberFormat
import java.util.*

class ActivityDetailRiwayat : AppCompatActivity() {

    private lateinit var viewModelRiwayat: ViewModelRiwayat
    private lateinit var adapter: DetailRiwayatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_riwayat)
        setupUI()
        setupViewModel()
        setupObserver()
        val idTransaksi = intent.getBundleExtra("historyTransaction").get("idTransaksi").toString()
        val totalOngkir = intent.getBundleExtra("historyTransaction").get("totalOngkir").toString()
        val totalTransaksi = intent.getBundleExtra("historyTransaction").get("totalTransaksi").toString()

        viewModelRiwayat.fetchDetailTransaksi(idTransaksi)
        tv_detail_riwayat_total_ongkir.text = "Rp. "+ getFormat(totalOngkir.toInt())
        tv_detail_riwayat_total_price.text =  "Rp. "+ getFormat(totalTransaksi.toInt())


    }

    fun getFormat(int: Int): String{
        val idLocale = Locale("id", "ID")
        val nf = NumberFormat.getNumberInstance(idLocale)
        return nf.format(int)
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
        viewModelRiwayat = ViewModelProviders.of(
            this,
            ViewModelFactory(this,is_remote)
        ).get(ViewModelRiwayat::class.java)
    }

    private fun setupObserver() {
        viewModelRiwayat.getDetailTrasaksi().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBarDetailRiwayat.visibility = View.GONE
                    it.data?.let {
                            users -> renderList(users)
                    }
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

    private fun renderList(item: List<DetailTransaksiData>) {
        adapter.addData(item)
        adapter.notifyDataSetChanged()
    }
}
