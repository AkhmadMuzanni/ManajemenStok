package project.manajemenstok.ui.main.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_pembelian.*
import kotlinx.android.synthetic.main.fragment_pembelian.view.*
import kotlinx.android.synthetic.main.item_pembelian_layout.view.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.DataPembelianAdapter
import project.manajemenstok.ui.main.adapter.MainAdapter
import project.manajemenstok.ui.main.view.InputBarangActivity
import project.manajemenstok.ui.main.view.KonfirmasiPembelianActivity
import project.manajemenstok.ui.main.view.MainActivity
import project.manajemenstok.ui.main.viewmodel.MainViewModel
import project.manajemenstok.ui.main.viewmodel.PembelianViewModel
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.NumberTextWatcher
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class FragmentPembelian : Fragment(), View.OnClickListener, View.OnFocusChangeListener{

    private val INPUT_BARANG_INTENT = 1
    private val KONFIRMASI_INTENT = 2
    private lateinit var pembelianViewModel: PembelianViewModel
    private lateinit var viewPembelian: View
    private lateinit var dataPembelianAdapter: DataPembelianAdapter
    private lateinit var rvDataPembelian: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewPembelian =  inflater!!.inflate(R.layout.fragment_pembelian, container, false)
        setupViewModel()
        setupUI()

        if(MainActivity.tempPembelian.getSerializable("dataPenjual") != null){
            viewPembelian.text_input_penjual.setText(MainActivity.tempPembelian.getString("dataPenjual"))
            viewPembelian.text_ongkir_pembelian.setText(MainActivity.tempPembelian.getString("dataOngkir"))
            viewPembelian.text_input_total.setText(MainActivity.tempPembelian.getString("dataTotal"))

            if(MainActivity.tempPembelian.getString("dataOngkir") != ""){
                pembelianViewModel.setTempOngkir(getAngka(MainActivity.tempPembelian.getString("dataOngkir")))
            }
        }

        viewPembelian.input_barang_pembelian.setOnClickListener(this)
        viewPembelian.input_barang_pembelian.setOnFocusChangeListener(this)
        viewPembelian.btn_input_barang.setOnClickListener(this)
        viewPembelian.text_ongkir_pembelian.setOnFocusChangeListener(this)


        onTextChangeListener()

        return viewPembelian
    }

    override fun onResume() {
        super.onResume()
        var tempBarang = pembelianViewModel.getTempBarang()
        if(MainActivity.tempPembelian.getSerializable("dataBarang") != null){
            tempBarang = MainActivity.tempPembelian.getSerializable("dataBarang") as ArrayList<Barang>
            pembelianViewModel.setTempBarang(tempBarang)
        }
        renderDataPembelian(tempBarang)
    }

    private fun setupUI(){
        rvDataPembelian = viewPembelian.rv_data_pembelian
        rvDataPembelian.layoutManager = LinearLayoutManager(viewPembelian.context)
        dataPembelianAdapter = DataPembelianAdapter(arrayListOf(), pembelianViewModel, this)
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
            ViewModelFactory(viewPembelian.context,is_remote)
        ).get(PembelianViewModel::class.java)
    }

    private fun renderDataPembelian(barangs: List<Barang>) {
        dataPembelianAdapter.setData(barangs)
        dataPembelianAdapter.notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        var bundle = Bundle()
        when (v.id){
            R.id.input_barang_pembelian->{
                goToInput(v)
            }
            R.id.btn_input_barang->{
                var bundlePenjual = Bundle()
                bundlePenjual.putString("namaPenjual", viewPembelian.text_input_penjual.text.toString())
                bundlePenjual.putString("noTelp", "")

                bundle.putBundle("dataPenjual", bundlePenjual)
                bundle.putString("dataOngkir", getAngka(viewPembelian.text_ongkir_pembelian.text.toString()).toString())
                bundle.putString("dataTotal", getAngka(viewPembelian.text_input_total.text.toString()).toString())
                bundle.putString("dataSubtotal", pembelianViewModel.getSubtotal().toString())
                bundle.putSerializable("dataBarang", pembelianViewModel.getTempBarang())

                val konfirmasiPembelianIntent =  Intent(v.context, KonfirmasiPembelianActivity::class.java)
                konfirmasiPembelianIntent.putExtra("dataPembelian", bundle)
                konfirmasiPembelianIntent.putExtra("parentActivity", Constants.JenisTransaksiValue.PEMBELIAN)
                startActivityForResult(konfirmasiPembelianIntent, Constants.RequestCodeIntent.KONFIRMASI_TRANSAKSI)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == INPUT_BARANG_INTENT && data != null && resultCode == RESULT_OK){
            val tempBarang = Barang()
            tempBarang.id = data?.getBundleExtra("bundle")?.getInt("id")!!
            tempBarang.namaBarang = data?.getBundleExtra("bundle")?.getString("namaBarang")!!
            tempBarang.foto = data?.getBundleExtra("bundle")?.getString("foto")!!
            tempBarang.jumlah = 1
            tempBarang.harga = 0
            tempBarang.total = 0
            tempBarang.uuid = data?.getBundleExtra("bundle")?.getString("uuid")!!
//            input_barang_pembelian.setText(tempBarang.namaBarang)
            pembelianViewModel.addTempBarang(tempBarang)
        } else if(requestCode == KONFIRMASI_INTENT && data != null && resultCode == RESULT_OK){
            destroyView(false)
            activity?.finish()
        }
    }

    fun destroyView(saveDraft: Boolean){
//        Toast.makeText(context, "Masuk", Toast.LENGTH_LONG).show()
        val bundle = Bundle()
        if(saveDraft){
            bundle.putString("dataPenjual", viewPembelian.text_input_penjual.text.toString())
            bundle.putString("dataOngkir", viewPembelian.text_ongkir_pembelian.text.toString())
            bundle.putString("dataTotal", viewPembelian.text_input_total.text.toString())
            bundle.putSerializable("dataBarang", pembelianViewModel.getTempBarang())
        }
//        MainActivity.setTempData(bundle)
        MainActivity.tempPembelian = bundle
    }

    private fun onTextChangeListener(){
        viewPembelian.text_ongkir_pembelian.addTextChangedListener(object: NumberTextWatcher(viewPembelian.text_ongkir_pembelian){
            override fun afterTextChanged(s: Editable) {
                super.afterTextChanged(s)
                if(s.toString() == ""){
                    pembelianViewModel.setTempOngkir(0)
                } else {
                    pembelianViewModel.setTempOngkir(getAngka(viewPembelian.text_ongkir_pembelian.text.toString()))
                }
                viewPembelian.text_input_total.setText(getFormat(Integer.parseInt(pembelianViewModel.getTotalTransaksi().toString())))
            }
        })
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        when (v.id){
            R.id.text_ongkir_pembelian->{
                if(viewPembelian.text_ongkir_pembelian.text.toString() == "" && !hasFocus){
                    viewPembelian.text_ongkir_pembelian.setText("0")
                }
            }
            R.id.input_barang_pembelian->{
                if(hasFocus){
                    goToInput(v)
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

    fun goToInput(v: View){
        var bundle = Bundle()

        val inputBarangIntent =  Intent(v.context, InputBarangActivity::class.java)
        if(pembelianViewModel.getBarangUsed().size != 0){
            bundle.putSerializable("dataBarangUsed", pembelianViewModel.getBarangUsed())
            bundle.putBoolean("isBarangUsed", true)
            inputBarangIntent.putExtra("dataTransaksi", bundle)
        }

        inputBarangIntent.putExtra("parent",0)

        startActivityForResult(inputBarangIntent, Constants.RequestCodeIntent.INPUT_BARANG)
    }

}