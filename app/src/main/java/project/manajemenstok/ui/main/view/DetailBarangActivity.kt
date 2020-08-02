package project.manajemenstok.ui.main.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_detail_barang.*
import kotlinx.android.synthetic.main.activity_penjualan.icon_back
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.viewmodel.BarangViewModel
import project.manajemenstok.utils.Helper

class DetailBarangActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var barangViewModel: BarangViewModel
    private lateinit var objBarang: Barang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_barang)

        AndroidThreeTen.init(this)

        setupViewModel()

        objBarang = intent.getSerializableExtra("dataBarang") as Barang

        input_barang.setText(objBarang.namaBarang)
        input_kategori.setText("Celana Anak")
        input_jumlah.setText(Helper.getFormat(objBarang.jumlah))
        input_harga.setText(Helper.getFormat(objBarang.harga))

        barangViewModel.getImageUrl().observe(this, Observer {
            Glide.with(image_view_barang.context).load(it.toString()).into(image_view_barang)
        })

        barangViewModel.fetchImageUrl("bevyStock/default.png")

        icon_back.setOnClickListener(this)
        btn_edit.setOnClickListener(this)
        btn_simpan.setOnClickListener(this)

    }

    override fun onBackPressed() {
        findViewById<ImageView>(R.id.icon_back).performClick()
    }

    private fun setupViewModel() {
        val is_remote = true
        barangViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(applicationContext, is_remote)
        ).get(BarangViewModel::class.java)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.icon_back->{
                finish()
            }
            R.id.btn_edit->{
                v.visibility = View.GONE
                btn_simpan.visibility = View.VISIBLE

                setEnable(true)

                input_barang.requestFocus()
                val keyboard = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                keyboard?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            }
            R.id.btn_simpan->{
                updateValue()
                setEnable(false)

                v.visibility = View.GONE
                btn_edit.visibility = View.VISIBLE

                barangViewModel.saveBarang(objBarang)
                Toast.makeText(v.context, "Perubahan berhasil disimpan", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setEnable(isEnable: Boolean){
        input_barang.isFocusable = isEnable
        input_barang.isFocusableInTouchMode = isEnable

        input_kategori.isFocusable = isEnable
        input_kategori.isFocusableInTouchMode = isEnable

        input_jumlah.isFocusable = isEnable
        input_jumlah.isFocusableInTouchMode = isEnable

        input_harga.isFocusable = isEnable
        input_harga.isFocusableInTouchMode = isEnable
    }

    private fun updateValue(){
        objBarang.namaBarang = input_barang.text.toString()
        objBarang.jumlah = Helper.getAngka(input_jumlah.text.toString())
        objBarang.harga = Helper.getAngka(input_harga.text.toString())
    }
}
