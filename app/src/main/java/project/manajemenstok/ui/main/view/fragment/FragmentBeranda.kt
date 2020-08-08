package project.manajemenstok.ui.main.view.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.fragment_beranda.view.*

import project.manajemenstok.R
import project.manajemenstok.data.model.TransaksiData
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.OnRiwayatItemClickListener
import project.manajemenstok.ui.main.adapter.RiwayatAdapter
import project.manajemenstok.ui.main.view.activity.DetailRiwayatActivity
import project.manajemenstok.ui.main.view.activity.PembelianActivity
import project.manajemenstok.ui.main.view.activity.PenjualanActivity
import project.manajemenstok.ui.main.viewmodel.RiwayatViewModel
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Status
import java.text.NumberFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentBeranda : Fragment(), View.OnClickListener, OnRiwayatItemClickListener {

    private lateinit var riwayatViewModel: RiwayatViewModel
    private lateinit var adapter: RiwayatAdapter
    private lateinit var viewFragmentBeranda: View
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewFragmentBeranda = inflater.inflate(R.layout.fragment_beranda, container, false)
        viewFragmentBeranda.btn_pembelian.setOnClickListener(this)
        viewFragmentBeranda.btn_penjualan.setOnClickListener(this)
        setupUI()
        setupViewModel()
        setupObserver()
        riwayatViewModel.fetchTransaksiRepository()
        return viewFragmentBeranda
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupUI(){
        rv = viewFragmentBeranda.findViewById(R.id.rv_riwayat_transaksi)
        rv.layoutManager = LinearLayoutManager(viewFragmentBeranda.context)
        adapter = RiwayatAdapter(arrayListOf(), this)
        rv.addItemDecoration(
            DividerItemDecoration(
                rv.context,
                (rv.layoutManager as LinearLayoutManager).orientation
            )
        )
        rv.adapter = adapter
    }

    private fun setupViewModel() {
        val is_remote = true
        riwayatViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(viewFragmentBeranda.context,is_remote)
        ).get(RiwayatViewModel::class.java)
    }

    private fun setupObserver() {
        riwayatViewModel.getTransaksi().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBarBeranda.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    rv.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBarBeranda.visibility = View.VISIBLE
                    rv.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBarBeranda.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(transaksiList: List<TransaksiData>) {
        adapter.setData(transaksiList.reversed())
        adapter.notifyDataSetChanged()
        countTotalBalance(transaksiList)
    }

    private fun countTotalBalance(transaksiList: List<TransaksiData>){
        var totalPembelian = 0
        var totalPenjualan = 0
        for (transaksi in transaksiList ){
            if (Constants.JenisTransaksiValue.PEMBELIAN==transaksi.jenisTransaksi){
                totalPembelian += transaksi.totalTransaksi
            }else{
                totalPenjualan += transaksi.totalTransaksi
            }
        }
        viewFragmentBeranda.tv_total_pemasukan.text = "Rp. "+getFormat(totalPembelian)
        viewFragmentBeranda.tv_total_pengeluaran.text = "Rp. "+getFormat(totalPenjualan)
    }

    fun getFormat(int: Int): String{
        val idLocale = Locale("id", "ID")
        val nf = NumberFormat.getNumberInstance(idLocale)
        return nf.format(int)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btn_pembelian->{
//                Toast.makeText(applicationContext, "Ini adalah contoh Toast di Android", Toast.LENGTH_LONG).show()
                val pembelianIntent = Intent(this.context, PembelianActivity::class.java)
                startActivity(pembelianIntent)
            }
            R.id.btn_penjualan->{
                val penjualanIntent = Intent(this.context, PenjualanActivity::class.java)
                startActivity(penjualanIntent)
//                val dbBarang = mainViewModel.getDbReference("barang")
//                val key = dbBarang.push().key
//                dbBarang.child(key!!).setValue(Barang(0, "Piyama", 222, "https://cdn.pixabay.com/photo/2014/04/03/10/55/t-shirt-311732_1280.png", 1, 1))
//                Toast.makeText(context, "Berhasil menambah barang", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onItemClick(item: TransaksiData, position: Int) {
        val historyTransaction = Intent(viewFragmentBeranda.context, DetailRiwayatActivity::class.java)
        val historyTransactionBundle = Bundle()
        historyTransactionBundle.putString("idTransaksi", item.uuid)
        historyTransactionBundle.putInt("totalTransaksi", item.totalTransaksi)
        historyTransactionBundle.putInt("totalOngkir", item.ongkir)
        historyTransaction.putExtra("historyTransaction", historyTransactionBundle)
        startActivity(historyTransaction)
    }


}
