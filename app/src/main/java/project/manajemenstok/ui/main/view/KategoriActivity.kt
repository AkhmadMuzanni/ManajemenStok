package project.manajemenstok.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_kategori.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.KategoriGridAdapter
import project.manajemenstok.ui.main.viewmodel.KategoriViewModel
import project.manajemenstok.utils.Status
import kotlin.collections.ArrayList

class KategoriActivity : AppCompatActivity() {

    private lateinit var kategoriViewModel: KategoriViewModel
    private lateinit var gridAdapter: KategoriGridAdapter

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

        kategoriViewModel.fetchKategori()
    }

    private fun setupUI(){
        gridAdapter = KategoriGridAdapter(this, R.layout.item_kategori_layout, arrayListOf())
        grid_kategori.adapter = gridAdapter
    }

    private fun setupViewModel() {
        val is_remote = true
        kategoriViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(this,is_remote)
        ).get(KategoriViewModel::class.java)
    }

    private fun renderList(item: ArrayList<Kategori>) {
        gridAdapter.setData(item)
        gridAdapter.notifyDataSetChanged()
    }
}
