package project.manajemenstok.ui.main.view.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_detail_kategori.*
import kotlinx.android.synthetic.main.activity_penjualan.icon_back
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.ListBarangKategoriAdapter
import project.manajemenstok.ui.main.viewmodel.ViewModelBarang
import project.manajemenstok.ui.main.viewmodel.ViewModelKategori
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Status

class ActivityDetailKategori : AppCompatActivity(), View.OnClickListener{
    private lateinit var kategoriViewModel: ViewModelKategori
    private lateinit var barangViewModel: ViewModelBarang
    private lateinit var objKategori: Kategori
    private var listKategori = ArrayList<Kategori>()
    private var intentMode = 0
    private lateinit var tempImageUri: Uri
    private var isImageChange = false
    private lateinit var adapter: ListBarangKategoriAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kategori)

        AndroidThreeTen.init(this)

        setupViewModel()
        setupUI()

        intentMode = intent.getIntExtra("intentMode", 0)

        kategoriViewModel.getBarangKategori().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { barang ->
                        if(barang.size == 0){
                            rv_barang_kategori.visibility = View.GONE
                            text_empty_state.visibility = View.VISIBLE
                        } else {
                            rv_barang_kategori.visibility = View.VISIBLE
                            text_empty_state.visibility = View.GONE

                            renderList(barang)
                        }
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    //Handle Error
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        Glide.with(image_view_kategori.context).load(resources.getString(R.string.defaultImageIcon)).into(image_view_kategori)

        icon_back.setOnClickListener(this)
        btn_edit.setOnClickListener(this)
        btn_simpan.setOnClickListener(this)
        image_view_kategori.setOnClickListener(this)
        icon_delete.setOnClickListener(this)

    }

    public override fun onResume() {
        super.onResume()

        if(intentMode == Constants.IntentMode.EDIT){
            objKategori = intent.getSerializableExtra("dataKategori") as Kategori
            listKategori = intent.getSerializableExtra("listKategori") as ArrayList<Kategori>

            input_kategori.setText(objKategori.nama)
            Glide.with(image_view_kategori.context).load(objKategori.foto).into(image_view_kategori)
            kategoriViewModel.fetchBarangKategori(objKategori.uuid)
        } else {
            btn_edit.visibility = View.GONE
            rv_barang_kategori.visibility = View.GONE
            separator.visibility = View.GONE
            text_empty_state.visibility = View.GONE
            setEnable(true)
        }
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

                    if(intentMode == Constants.IntentMode.EDIT){
                        kategoriViewModel.getUploadResult().observe(this, Observer {
                            objKategori.foto = it
                            kategoriViewModel.saveKategori(objKategori)
                            Glide.with(image_view_kategori.context).load(it).into(image_view_kategori)
                            Toast.makeText(this, "Foto Kategori Berhasil Diubah", Toast.LENGTH_LONG).show()
                        })
//
                        kategoriViewModel.uploadImage(imageUri!!, resources.getString(R.string.bucketStorage) + objKategori.uuid + resources.getString(R.string.extensionImage))
                    } else {
                        Glide.with(image_view_kategori.context).load(imageUri).into(image_view_kategori)
                        tempImageUri = imageUri!!
                        isImageChange = true
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        val is_remote = true
        kategoriViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(applicationContext, is_remote)
        ).get(ViewModelKategori::class.java)

        barangViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(applicationContext, is_remote)
        ).get(ViewModelBarang::class.java)
    }

    private fun setupUI(){
        rv_barang_kategori.layoutManager = LinearLayoutManager(this)
        adapter = ListBarangKategoriAdapter(arrayListOf(), this, barangViewModel)
        rv_barang_kategori.addItemDecoration(
            DividerItemDecoration(
                rv_barang_kategori.context,
                (rv_barang_kategori.layoutManager as LinearLayoutManager).orientation
            )
        )
        rv_barang_kategori.adapter = adapter
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.icon_back->{
                val resultKategoriIntent = Intent()
                setResult(Activity.RESULT_CANCELED, resultKategoriIntent)
                finish()
            }
            R.id.btn_edit->{
                v.visibility = View.GONE
                btn_simpan.visibility = View.VISIBLE

                setEnable(true)

                input_kategori.requestFocus()
                val keyboard = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                keyboard?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            }
            R.id.btn_simpan->{
                if(intentMode == Constants.IntentMode.EDIT){
                    updateValue()
                    setEnable(false)

                    v.visibility = View.GONE
                    btn_edit.visibility = View.VISIBLE

                    kategoriViewModel.saveKategori(objKategori)

                    Toast.makeText(v.context, "Perubahan berhasil disimpan", Toast.LENGTH_LONG).show()
                } else {
                    objKategori = Kategori()
                    objKategori.nama = input_kategori.text.toString()
                    objKategori.foto = resources.getString(R.string.defaultImageIcon)
                    objKategori.jumlah = 0

                    val uuidSaved = kategoriViewModel.createKategori(objKategori)

                    val resultKategoriIntent = Intent()
                    resultKategoriIntent.putExtra("uuidNewKategori", uuidSaved)

                    if(isImageChange){
                        kategoriViewModel.getUploadResult().observe(this, Observer {
                            objKategori.foto = it
                            kategoriViewModel.saveKategori(objKategori)
                            progressBar_kategori.visibility = View.GONE
                            Toast.makeText(v.context, "Kategori berhasil disimpan", Toast.LENGTH_LONG).show()
                            setResult(Activity.RESULT_OK, resultKategoriIntent)
                            finish()
                        })

                        kategoriViewModel.uploadImage(tempImageUri, resources.getString(R.string.bucketStorage) + uuidSaved + resources.getString(R.string.extensionImage))
                        progressBar_kategori.visibility = View.VISIBLE
                        btn_simpan.isEnabled = false

                    } else {
                        Toast.makeText(v.context, "Kategori berhasil disimpan", Toast.LENGTH_LONG).show()
                        setResult(Activity.RESULT_OK, resultKategoriIntent)
                        finish()
                    }
                }
            }
            R.id.image_view_kategori->{
//                Toast.makeText(v.context, "Gambar Diganti", Toast.LENGTH_LONG).show()
                val imageIntent = Intent()
                imageIntent.setType("image/*")
                imageIntent.setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(imageIntent, Constants.RequestCodeIntent.GET_IMAGE)
            }
            R.id.icon_delete->{
                var builder = AlertDialog.Builder(this, R.style.CustomDialogTheme)
                builder.setTitle("Konfirmasi")
                builder.setMessage("Anda Yakin ingin menghapus " + objKategori.nama + " ?")

                builder.setPositiveButton("HAPUS") { _, _ ->
                    objKategori.isDeleted = Constants.DeleteStatus.DELETED
                    kategoriViewModel.saveKategori(objKategori)

                    Toast.makeText(this, "Kategori berhasil dihapus", Toast.LENGTH_LONG).show()
                    finish()
                }

                builder.setNegativeButton("BATAL") { _, _ ->
                }

                builder.show()
            }
        }
    }

    private fun setEnable(isEnable: Boolean){
        input_kategori.isFocusable = isEnable
        input_kategori.isFocusableInTouchMode = isEnable
    }

    private fun updateValue(){
        objKategori.nama = input_kategori.text.toString()
    }

    fun getListKategori(): ArrayList<Kategori>{
        return listKategori
    }

    private fun renderList(listBarang: ArrayList<Barang>) {
        adapter.setData(listBarang, listKategori)
        adapter.notifyDataSetChanged()
    }
}
