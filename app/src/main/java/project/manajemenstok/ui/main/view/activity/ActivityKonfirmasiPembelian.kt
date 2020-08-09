package project.manajemenstok.ui.main.view.activity

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
import project.manajemenstok.ui.main.viewmodel.ViewModelPembelian
import project.manajemenstok.ui.main.viewmodel.ViewModelPenjualan
import project.manajemenstok.utils.Constants
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class ActivityKonfirmasiPembelian : AppCompatActivity(), View.OnClickListener {
    private lateinit var pembelianViewModel: ViewModelPembelian
    private lateinit var penjualanViewModel: ViewModelPenjualan
    private lateinit var dataPembelianAdapter: KonfirmasiPembelianAdapter
    private lateinit var rvDataPembelian: RecyclerView
    private lateinit var bundlePembelian: Bundle
    private var parentActivity = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi_pembelian)

        parentActivity = intent.getIntExtra("parentActivity", Constants.JenisTransaksiValue.PEMBELIAN)
        if(parentActivity == Constants.JenisTransaksiValue.PEMBELIAN){
            txt_header_konfirmasi.text = "Konfirmasi Pembelian"
        } else {
            txt_header_konfirmasi.text = "Konfirmasi Penjualan"
        }

        setupViewModel()
        setupUI()

        bundlePembelian = intent.getBundleExtra("dataPembelian")
        val dataBarang = intent.getBundleExtra("dataPembelian").getSerializable("dataBarang") as ArrayList<Barang>
        val dataPenjual = intent.getBundleExtra("dataPembelian").getBundle("dataPenjual").getString("namaPenjual").capitalize()
        val dataOngkir = getFormat(Integer.parseInt(intent.getBundleExtra("dataPembelian").getString("dataOngkir")))
        val dataSubtotal = getFormat(Integer.parseInt(intent.getBundleExtra("dataPembelian").getString("dataSubtotal")))
        val dataTotal = getFormat(Integer.parseInt(intent.getBundleExtra("dataPembelian").getString("dataTotal")))

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
        if(parentActivity == Constants.JenisTransaksiValue.PEMBELIAN){
            pembelianViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(applicationContext,is_remote)
            ).get(ViewModelPembelian::class.java)
        } else {
            penjualanViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(applicationContext,is_remote)
            ).get(ViewModelPenjualan::class.java)
        }

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
                if(parentActivity == Constants.JenisTransaksiValue.PEMBELIAN){
                    val isSuccess = pembelianViewModel.simpanPembelian(bundlePembelian, this)
                    if(isSuccess){
                        val konfirmasiIntent = Intent()
                        konfirmasiIntent.putExtra("bundle",bundlePembelian)
                        setResult(Activity.RESULT_OK, konfirmasiIntent)
                        finish()
                    }
                } else {
                    val isSuccess = penjualanViewModel.simpanPenjualan(bundlePembelian, this)
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

    fun getAngka(string: String): Int{
        val idLocale = Locale("id", "ID")
        val nf = NumberFormat.getNumberInstance(idLocale)
        return nf.parse(string).toInt()
    }

    fun getFormat(int: Int): String{
        val idLocale = Locale("id", "ID")
        val nf = NumberFormat.getNumberInstance(idLocale)
        return nf.format(int)
    }
}
