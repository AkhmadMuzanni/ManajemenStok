package project.manajemenstok.ui.main.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_kategori.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.KategoriGridAdapter
import project.manajemenstok.ui.main.viewmodel.ViewModelKategori
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Helper
import project.manajemenstok.utils.Status
import kotlin.collections.ArrayList

class ActivityKategori : AppCompatActivity(), AdapterView.OnItemClickListener, View.OnClickListener, SearchView.OnQueryTextListener {

    private lateinit var kategoriViewModel: ViewModelKategori
    private lateinit var gridAdapter: KategoriGridAdapter
    private lateinit var listKategori: ArrayList<Kategori>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori)
        setupUI()
        setupViewModel()

        kategoriViewModel.getKategori().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { kategori ->
                        renderList(kategori)
                        listKategori = kategori
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

        Helper.setFontSizeSearchView(sv_kategori, 14f)
        sv_kategori.setIconifiedByDefault(false)

        btn_tambah_kategori.setOnClickListener(this)

        sv_kategori.setOnQueryTextListener(this)
    }

    override fun onResume() {
        super.onResume()

        val txt_search_kategori = Helper.getTextViewSearchView(sv_kategori)
        txt_search_kategori.setText("")

        kategoriViewModel.syncKategori()
    }

    private fun setupUI(){
        gridAdapter = KategoriGridAdapter(this, R.layout.item_kategori_layout, arrayListOf())
        grid_kategori.adapter = gridAdapter
        grid_kategori.onItemClickListener = this
    }

    private fun setupViewModel() {
        val is_remote = true
        kategoriViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(this,is_remote)
        ).get(ViewModelKategori::class.java)
    }

    private fun renderList(item: ArrayList<Kategori>) {
        gridAdapter.setData(item)
        gridAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val detailKategoriIntent =  Intent(parent?.context, ActivityDetailKategori::class.java)
        detailKategoriIntent.putExtra("dataKategori", listKategori[position])
        detailKategoriIntent.putExtra("intentMode", Constants.IntentMode.EDIT)
        detailKategoriIntent.putExtra("listKategori", listKategori)
        startActivityForResult(detailKategoriIntent, Constants.RequestCodeIntent.DETAIL_KATEGORI)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_tambah_kategori->{
                val detailKategoriIntent =  Intent(applicationContext, ActivityDetailKategori::class.java)
                detailKategoriIntent.putExtra("intentMode", Constants.IntentMode.ADD)
                startActivityForResult(detailKategoriIntent, Constants.RequestCodeIntent.DETAIL_KATEGORI)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText == ""){
            kategoriViewModel.syncKategori()
        } else {
            kategoriViewModel.syncKategori(newText!!.toLowerCase())
        }
        return true
    }
}
