package project.manajemenstok.ui.main.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.DataPembelianAdapter
import project.manajemenstok.ui.main.adapter.MainAdapter
import project.manajemenstok.ui.main.view.InputBarangActivity
import project.manajemenstok.ui.main.view.MainActivity
import project.manajemenstok.ui.main.viewmodel.MainViewModel
import project.manajemenstok.ui.main.viewmodel.PembelianViewModel


class FragmentPembelian : Fragment(), View.OnClickListener{

    private val INPUT_BARANG_INTENT = 1
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

        if(MainActivity.getTempData().getSerializable("dataPenjual") != null){
            viewPembelian.text_input_penjual.setText(MainActivity.getTempData().getString("dataPenjual"))
            viewPembelian.text_ongkir_pembelian.setText(MainActivity.getTempData().getString("dataOngkir"))
            viewPembelian.text_input_total.setText(MainActivity.getTempData().getString("dataTotal"))

            pembelianViewModel.setTempOngkir(Integer.parseInt(MainActivity.getTempData().getString("dataOngkir")))
        }

        viewPembelian.input_barang_pembelian.setOnClickListener(this)
        viewPembelian.btn_input_barang.setOnClickListener(this)
        viewPembelian.input_barang_pembelian.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                val inputBarangIntent =  Intent(context, InputBarangActivity::class.java)
                startActivityForResult(inputBarangIntent, INPUT_BARANG_INTENT)
            }
        }

        onTextChangeListener()

        return viewPembelian
    }

    override fun onResume() {
        super.onResume()
        var tempBarang = pembelianViewModel.getTempBarang()
        if(MainActivity.getTempData().getSerializable("dataBarang") != null){
            tempBarang = MainActivity.getTempData().getSerializable("dataBarang") as ArrayList<Barang>
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
//        dataPembelianAdapter.addData(barangs)
        dataPembelianAdapter.setData(barangs)
        dataPembelianAdapter.notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.input_barang_pembelian->{
                val inputBarangIntent =  Intent(v.context, InputBarangActivity::class.java)
                startActivityForResult(inputBarangIntent, INPUT_BARANG_INTENT)
            }
            R.id.btn_input_barang->{
//                val konfirmasiPembelianIntent =  Intent(v.context, KonfirmasiPembelianActivity::class.java)
//                startActivity(konfirmasiPembelianIntent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && data != null && resultCode == RESULT_OK){
            val tempBarang = Barang()
            tempBarang.id = data?.getBundleExtra("bundle")?.getInt("id")!!
            tempBarang.namaBarang = data?.getBundleExtra("bundle")?.getString("namaBarang")!!
            tempBarang.foto = resources.getString(R.string.defaultImageIcon)
            tempBarang.jumlah = 1
            tempBarang.harga = 0
            tempBarang.total = 0
//            input_barang_pembelian.setText(tempBarang.namaBarang)
            pembelianViewModel.addTempBarang(tempBarang)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bundle = Bundle()
        bundle.putString("dataPenjual", viewPembelian.text_input_penjual.text.toString())
        bundle.putString("dataOngkir", viewPembelian.text_ongkir_pembelian.text.toString())
        bundle.putString("dataTotal", viewPembelian.text_input_total.text.toString())
        bundle.putSerializable("dataBarang", pembelianViewModel.getTempBarang())
        MainActivity.setTempData(bundle)
//        Toast.makeText(context, a.size.toString(), Toast.LENGTH_LONG).show()
    }

    private fun onTextChangeListener(){
        viewPembelian.text_ongkir_pembelian.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                pembelianViewModel.setTempOngkir(Integer.parseInt(s.toString()))
                viewPembelian.text_input_total.setText(pembelianViewModel.getTotalTransaksi().toString())
            }
        })
    }

}