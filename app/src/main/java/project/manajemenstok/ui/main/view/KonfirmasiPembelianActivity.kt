package project.manajemenstok.ui.main.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_konfirmasi_pembelian.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.KonfirmasiPembelianAdapter
import project.manajemenstok.ui.main.viewmodel.PembelianViewModel

class KonfirmasiPembelianActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var pembelianViewModel: PembelianViewModel
    private lateinit var dataPembelianAdapter: KonfirmasiPembelianAdapter
    private lateinit var rvDataPembelian: RecyclerView
    private lateinit var bundlePembelian: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi_pembelian)

        setupViewModel()
        setupUI()

        bundlePembelian = intent.getBundleExtra("dataPembelian")
        val dataBarang = intent.getBundleExtra("dataPembelian").getSerializable("dataBarang") as ArrayList<Barang>
        val dataPenjual = intent.getBundleExtra("dataPembelian").getBundle("dataPenjual").getString("namaPenjual").capitalize()
        val dataOngkir = intent.getBundleExtra("dataPembelian").getString("dataOngkir")
        val dataSubtotal = intent.getBundleExtra("dataPembelian").getString("dataSubtotal")
        val dataTotal = intent.getBundleExtra("dataPembelian").getString("dataTotal")

        findViewById<TextView>(R.id.value_penjual).setText(dataPenjual)
        findViewById<TextView>(R.id.value_ongkir).setText(dataOngkir)
        findViewById<TextView>(R.id.value_total).setText(dataTotal)
        findViewById<TextView>(R.id.value_subtotal).setText(dataSubtotal)
        renderDataPembelian(dataBarang)

        val iconBack : ImageView = findViewById(R.id.icon_back)

        iconBack.setOnClickListener(this)
        btn_simpan_transaksi.setOnClickListener(this)
    }

    private fun setupUI(){
        rvDataPembelian = findViewById(R.id.rv_data_pembelian)
        rvDataPembelian.layoutManager = LinearLayoutManager(applicationContext)
        dataPembelianAdapter = KonfirmasiPembelianAdapter(arrayListOf())
        rvDataPembelian.addItemDecoration(
            DividerItemDecoration(
                rvDataPembelian.context,
                (rvDataPembelian.layoutManager as LinearLayoutManager).orientation
            )
        )
        rvDataPembelian.adapter = dataPembelianAdapter
    }

    private fun setupViewModel() {
        val is_remote = true
        pembelianViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(applicationContext,is_remote)
        ).get(PembelianViewModel::class.java)
    }

    private fun renderDataPembelian(barangs: List<Barang>) {
        dataPembelianAdapter.setData(barangs)
        dataPembelianAdapter.notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.icon_back->{
                val konfirmasiIntent = Intent()
                val konfirmasiBundle = Bundle()
                konfirmasiIntent.putExtra("bundle",konfirmasiBundle)
                setResult(Activity.RESULT_CANCELED, konfirmasiIntent)
                finish()
            }
            R.id.btn_simpan_transaksi->{
                val isSuccess = pembelianViewModel.simpanPembelian(bundlePembelian)
                if(isSuccess){
                    val konfirmasiIntent = Intent()
                    konfirmasiIntent.putExtra("bundle",bundlePembelian)
                    setResult(Activity.RESULT_OK, konfirmasiIntent)
                    finish()
                }
            }
        }
    }
}
