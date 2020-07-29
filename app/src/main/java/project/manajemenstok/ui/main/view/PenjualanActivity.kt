package project.manajemenstok.ui.main.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_penjualan.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.DataPenjualanAdapter
import project.manajemenstok.ui.main.viewmodel.PenjualanViewModel
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.NumberTextWatcher
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class PenjualanActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener {
    private lateinit var penjualanViewModel: PenjualanViewModel
    private lateinit var dataPenjualanAdapter: DataPenjualanAdapter
    private lateinit var rvDataPenjualan: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penjualan)

        AndroidThreeTen.init(this)

        setupViewModel()
        setupUI()

        if(MainActivity.getTempData().getSerializable("dataPenjual") != null){
            text_input_pembeli.setText(MainActivity.getTempData().getString("dataPenjual"))
            text_ongkir_pembelian.setText(MainActivity.getTempData().getString("dataOngkir"))
            text_input_total.setText(MainActivity.getTempData().getString("dataTotal"))

            if(MainActivity.getTempData().getString("dataOngkir") != ""){
                penjualanViewModel.setTempOngkir(getAngka(MainActivity.getTempData().getString("dataOngkir")))
            }
        }

        val iconBack : ImageView = findViewById(R.id.icon_back)

        input_barang_penjualan.setOnClickListener(this)
        iconBack.setOnClickListener(this)
        btn_input_barang.setOnClickListener(this)

        input_barang_penjualan.onFocusChangeListener = this

        onTextChangeListener()
    }

    override fun onResume() {
        super.onResume()
        var tempBarang = penjualanViewModel.getTempBarang()
        if(MainActivity.getTempData().getSerializable("dataBarang") != null){
            tempBarang = MainActivity.getTempData().getSerializable("dataBarang") as ArrayList<Barang>
            penjualanViewModel.setTempBarang(tempBarang)
        }
        renderDataPenjualan(tempBarang)
    }

    override fun onBackPressed() {
        findViewById<ImageView>(R.id.icon_back).performClick()
    }

    private fun setupUI(){
        rvDataPenjualan = findViewById(R.id.rv_data_penjualan)
        rvDataPenjualan.layoutManager = LinearLayoutManager(applicationContext)
        dataPenjualanAdapter = DataPenjualanAdapter(arrayListOf(), penjualanViewModel, this)
        rvDataPenjualan.addItemDecoration(
            DividerItemDecoration(
                rvDataPenjualan.context,
                (rvDataPenjualan.layoutManager as LinearLayoutManager).orientation
            )
        )
        rvDataPenjualan.adapter = dataPenjualanAdapter
    }

    private fun setupViewModel() {
        val is_remote = true
        penjualanViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(applicationContext,is_remote)
        ).get(PenjualanViewModel::class.java)
    }

    private fun renderDataPenjualan(barangs: List<Barang>) {
        dataPenjualanAdapter.setData(barangs)
        dataPenjualanAdapter.notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        var bundle = Bundle()
        when (v.id){
            R.id.input_barang_penjualan->{
                goToInput(v)
            }
            R.id.icon_back->{
                var builder = AlertDialog.Builder(this, R.style.CustomDialogTheme)
                builder.setTitle("Konfirmasi")
                builder.setMessage("Simpan Sebagai Draft?")

                builder.setPositiveButton("KEMBALI") { dialog, which ->
                }

                builder.setNegativeButton("HAPUS") { dialog, which ->
                    destroyView(false)
                    finish()
                }

                builder.setNeutralButton("SIMPAN DRAFT") { dialog, which ->
                    destroyView(true)
                    finish()
                }
                builder.show()
            }
            R.id.btn_input_barang->{
                var bundlePenjual = Bundle()
                bundlePenjual.putString("namaPenjual", text_input_pembeli.text.toString())
                bundlePenjual.putString("noTelp", "")

                bundle.putBundle("dataPenjual", bundlePenjual)
                bundle.putString("dataOngkir", getAngka(text_ongkir_pembelian.text.toString()).toString())
                bundle.putString("dataTotal", getAngka(text_input_total.text.toString()).toString())
                bundle.putString("dataSubtotal", penjualanViewModel.getSubtotal().toString())
                bundle.putSerializable("dataBarang", penjualanViewModel.getTempBarang())

                val konfirmasiPembelianIntent =  Intent(v.context, KonfirmasiPembelianActivity::class.java)
                konfirmasiPembelianIntent.putExtra("dataPembelian", bundle)
                konfirmasiPembelianIntent.putExtra("parentActivity", Constants.JenisTransaksiValue.PENJUALAN)
                startActivityForResult(konfirmasiPembelianIntent, Constants.RequestCodeIntent.KONFIRMASI_TRANSAKSI)
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

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        when (v.id){
            R.id.input_barang_penjualan->{
                if (hasFocus) {
                    goToInput(v)
                }
            }
        }
    }

    fun goToInput(v: View){
        var bundle = Bundle()

        val inputBarangIntent =  Intent(v.context, InputBarangActivity::class.java)
        if(penjualanViewModel.getBarangUsed().size != 0){
            bundle.putSerializable("dataBarangUsed", penjualanViewModel.getBarangUsed())
            bundle.putBoolean("isBarangUsed", true)
            inputBarangIntent.putExtra("dataTransaksi", bundle)
        }

        inputBarangIntent.putExtra("parent",1)

        startActivityForResult(inputBarangIntent, Constants.RequestCodeIntent.INPUT_BARANG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Constants.RequestCodeIntent.INPUT_BARANG && data != null && resultCode == Activity.RESULT_OK){
            val tempBarang = Barang()
            tempBarang.id = data?.getBundleExtra("bundle")?.getInt("id")!!
            tempBarang.namaBarang = data?.getBundleExtra("bundle")?.getString("namaBarang")!!
            tempBarang.foto = resources.getString(R.string.defaultImageIcon)
            tempBarang.jumlah = 1
            tempBarang.harga = data?.getBundleExtra("bundle")?.getInt("harga")!!
            tempBarang.total = 0
            tempBarang.uuid = data?.getBundleExtra("bundle")?.getString("uuid")!!
            tempBarang.maxQuantity = data?.getBundleExtra("bundle")?.getInt("maxQuantity")!!
            penjualanViewModel.addTempBarang(tempBarang)
        } else if(requestCode == Constants.RequestCodeIntent.KONFIRMASI_TRANSAKSI && data != null && resultCode == Activity.RESULT_OK){
            destroyView(false)
            finish()
        }
    }

    private fun onTextChangeListener(){
        text_ongkir_pembelian.addTextChangedListener(object: NumberTextWatcher(text_ongkir_pembelian){
            override fun afterTextChanged(s: Editable) {
                super.afterTextChanged(s)
                if(s.toString() == ""){
                    penjualanViewModel.setTempOngkir(0)
                } else {
                    penjualanViewModel.setTempOngkir(getAngka(text_ongkir_pembelian.text.toString()))
                }
                text_input_total.setText(getFormat(Integer.parseInt(penjualanViewModel.getTotalTransaksi().toString())))
            }
        })
    }

    fun destroyView(saveDraft: Boolean){
        val bundle = Bundle()
        if(saveDraft){
            bundle.putString("dataPenjual", text_input_pembeli.text.toString())
            bundle.putString("dataOngkir", text_ongkir_pembelian.text.toString())
            bundle.putString("dataTotal", text_input_total.text.toString())
            bundle.putSerializable("dataBarang", penjualanViewModel.getTempBarang())
        }
        MainActivity.setTempData(bundle)
    }
}
