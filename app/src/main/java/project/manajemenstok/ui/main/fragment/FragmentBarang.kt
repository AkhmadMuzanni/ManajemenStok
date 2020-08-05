package project.manajemenstok.ui.main.fragment


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
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.ListBarangAdapter
import project.manajemenstok.ui.main.adapter.ListKategoriAdapter
import project.manajemenstok.ui.main.view.MainActivity
import project.manajemenstok.ui.main.viewmodel.BarangViewModel
import project.manajemenstok.utils.Status

/**
 * A simple [Fragment] subclass.
 */
class FragmentBarang : Fragment() {

    private lateinit var barangViewModel: BarangViewModel
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
        barangViewModel.fetchLiveBarang()
        barangViewModel.fetchKategori()
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
        ).get(BarangViewModel::class.java)
    }

    private fun renderList(barangs: List<Barang>) {
        adapter.setData(barangs, MainActivity.tempKategori)
        adapter.notifyDataSetChanged()
    }

    private fun renderListKategori(kategori: List<Kategori>) {
        kategoriAdapter.setData(kategori)
        kategoriAdapter.notifyDataSetChanged()
    }

    fun getListKategori(): ArrayList<Kategori>{
        return listKategori
    }


}
