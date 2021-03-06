package project.manajemenstok.ui.main.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
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
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.viewmodel.ViewModelBarang
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Helper

class ActivityDetailBarang : AppCompatActivity(), View.OnClickListener{
    private lateinit var barangViewModel: ViewModelBarang
    private lateinit var objBarang: Barang
    private var listKategori = ArrayList<Kategori>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_barang)

        AndroidThreeTen.init(this)

        setupViewModel()

        objBarang = intent.getSerializableExtra("dataBarang") as Barang
        listKategori.addAll(intent.getSerializableExtra("dataKategori") as ArrayList<Kategori>)

        var arrayAdapter = ArrayAdapter<Kategori>(this, R.layout.support_simple_spinner_dropdown_item, listKategori)
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        kategori_spinner.adapter = arrayAdapter

        for (kategori in listKategori){
            if(kategori.uuid == objBarang.kategori){
                kategori_spinner.setSelection(arrayAdapter.getPosition(kategori))
            }
        }

        input_barang.setText(objBarang.namaBarang)
        input_jumlah.setText(Helper.getFormat(objBarang.jumlah))
        input_harga.setText(Helper.getFormat(objBarang.harga))
        Glide.with(image_view_barang.context).load(objBarang.foto).into(image_view_barang)

        icon_back.setOnClickListener(this)
        btn_edit.setOnClickListener(this)
        btn_simpan.setOnClickListener(this)
        image_view_barang.setOnClickListener(this)
        btn_riwayat_harga.setOnClickListener(this)

        kategori_spinner.isEnabled = false

    }

    override fun onBackPressed() {
        findViewById<ImageView>(R.id.icon_back).performClick()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            Constants.RequestCodeIntent.GET_IMAGE -> {
                if(resultCode == Activity.RESULT_OK){
                    val imageUri = data?.data

                    barangViewModel.getUploadResult().observe(this, Observer {
                        objBarang.foto = it
                        barangViewModel.saveBarang(objBarang)
                        Glide.with(image_view_barang.context).load(it).into(image_view_barang)
                        Toast.makeText(this, "Foto Barang Berhasil Diubah", Toast.LENGTH_LONG).show()
                    })

                    barangViewModel.uploadImage(imageUri!!, objBarang.uuid + resources.getString(R.string.extensionImage))
                }
            }
        }
    }

    private fun setupViewModel() {
        val is_remote = true
        barangViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(applicationContext, is_remote)
        ).get(ViewModelBarang::class.java)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.icon_back->{
                finish()
            }
            R.id.btn_riwayat_harga->{
                Toast.makeText(v.context, "Coming Soon", Toast.LENGTH_LONG).show()
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

                val objKategori = kategori_spinner.selectedItem as Kategori
                objBarang.kategori = objKategori.uuid
                barangViewModel.saveBarang(objBarang)

                Toast.makeText(v.context, "Perubahan berhasil disimpan", Toast.LENGTH_LONG).show()
            }
            R.id.image_view_barang->{
//                Toast.makeText(v.context, "Gambar Diganti", Toast.LENGTH_LONG).show()
                val imageIntent = Intent()
                imageIntent.setType("image/*")
                imageIntent.setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(imageIntent, Constants.RequestCodeIntent.GET_IMAGE)
            }
        }
    }

    private fun setEnable(isEnable: Boolean){
        input_barang.isFocusable = isEnable
        input_barang.isFocusableInTouchMode = isEnable

        kategori_spinner.isEnabled = isEnable

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
