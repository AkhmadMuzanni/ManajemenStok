package project.manajemenstok.ui.main.view.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_barang.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.ListBarangAdapter
import project.manajemenstok.ui.main.adapter.ListKategoriAdapter
import project.manajemenstok.ui.main.view.activity.ActivityKategori
import project.manajemenstok.ui.main.viewmodel.ViewModelBarang
import project.manajemenstok.utils.Status

/**
 * A simple [Fragment] subclass.
 */
class FragmentBarang : Fragment(), View.OnClickListener {

    private lateinit var barangViewModel: ViewModelBarang
    private lateinit var adapter: ListBarangAdapter
    private lateinit var viewFragmentBarang: View
    private lateinit var rv: RecyclerView
    private lateinit var rvKategori: RecyclerView
    private lateinit var kategoriAdapter: ListKategoriAdapter
    private var listKategori = ArrayList<Kategori>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragmentBarang = inflater.inflate(R.layout.fragment_barang, container, false)

        setupViewModel()
        setupUI()

        barangViewModel.getLiveBarang().observe(this, Observer {
            renderList(it)
        })

        barangViewModel.getKategori().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { kategori ->
                        renderListKategori(kategori)
                        listKategori.clear()
                        listKategori.addAll(kategori)
                        barangViewModel.fetchLiveBarang()
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    //Handle Error
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        return viewFragmentBarang
    }

    override fun onResume() {
        super.onResume()
        barangViewModel.syncKategori()
        barangViewModel.fetchKategori()

        btn_see_all_kategori.setOnClickListener(this)
    }

    private fun setupUI(){
        rv = viewFragmentBarang.findViewById(R.id.rv_beranda)
        rv.layoutManager = LinearLayoutManager(viewFragmentBarang.context)
        adapter = ListBarangAdapter(arrayListOf(), this, barangViewModel)
        rv.addItemDecoration(
            DividerItemDecoration(
                rv.context,
                (rv.layoutManager as LinearLayoutManager).orientation
            )
        )
        rv.adapter = adapter

        rvKategori = viewFragmentBarang.findViewById(R.id.rv_kategori)
        rvKategori.layoutManager = LinearLayoutManager(viewFragmentBarang.context, LinearLayoutManager.HORIZONTAL, false)
        kategoriAdapter = ListKategoriAdapter(arrayListOf(), this, barangViewModel)
        rvKategori.adapter = kategoriAdapter
    }

    private fun setupViewModel() {
        val is_remote = true
        barangViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(viewFragmentBarang.context,is_remote)
        ).get(ViewModelBarang::class.java)
    }

    private fun renderList(barangs: List<Barang>) {
        adapter.setData(barangs, listKategori)
        adapter.notifyDataSetChanged()
    }

    private fun renderListKategori(kategori: List<Kategori>) {
        kategoriAdapter.setData(kategori)
        kategoriAdapter.notifyDataSetChanged()
    }

    fun getListKategori(): ArrayList<Kategori>{
        return listKategori
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_see_all_kategori->{
                val kategoriIntent = Intent(this.context, ActivityKategori::class.java)
                startActivity(kategoriIntent)
            }
        }
    }


}
