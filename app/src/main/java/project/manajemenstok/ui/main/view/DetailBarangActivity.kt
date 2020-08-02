package project.manajemenstok.ui.main.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_detail_barang.*
import kotlinx.android.synthetic.main.activity_penjualan.icon_back
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.viewmodel.PenjualanViewModel
import java.text.NumberFormat
import java.util.*

class DetailBarangActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var penjualanViewModel: PenjualanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_barang)

        AndroidThreeTen.init(this)

        setupViewModel()

        val dataBarang: Barang = intent.getSerializableExtra("dataBarang") as Barang

        input_barang.setText(dataBarang.namaBarang)
        input_kategori.setText("Celana Anak")
        input_jumlah.setText(getFormat(dataBarang.jumlah))
        input_harga.setText(getFormat(dataBarang.harga))
        Glide.with(image_view_barang.context).load(dataBarang.foto).into(image_view_barang)

        icon_back.setOnClickListener(this)
        btn_edit.setOnClickListener(this)
        btn_simpan.setOnClickListener(this)

    }

    override fun onBackPressed() {
        findViewById<ImageView>(R.id.icon_back).performClick()
    }

    private fun setupViewModel() {
        val is_remote = true
        penjualanViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(applicationContext, is_remote)
        ).get(PenjualanViewModel::class.java)
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
                v.visibility = View.GONE
                btn_edit.visibility = View.VISIBLE
                setEnable(false)
                Toast.makeText(v.context, "Perubahan berhasil disimpan", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getAngka(string: String): Int {
        val idLocale = Locale("id", "ID")
        val nf = NumberFormat.getNumberInstance(idLocale)
        return nf.parse(string).toInt()
    }

    fun getFormat(int: Int): String {
        val idLocale = Locale("id", "ID")
        val nf = NumberFormat.getNumberInstance(idLocale)
        return nf.format(int)
    }

    fun setEnable(isEnable: Boolean){
        input_barang.isFocusable = isEnable
        input_barang.isFocusableInTouchMode = isEnable

        input_kategori.isFocusable = isEnable
        input_kategori.isFocusableInTouchMode = isEnable

        input_jumlah.isFocusable = isEnable
        input_jumlah.isFocusableInTouchMode = isEnable

        input_harga.isFocusable = isEnable
        input_harga.isFocusableInTouchMode = isEnable
    }
}
