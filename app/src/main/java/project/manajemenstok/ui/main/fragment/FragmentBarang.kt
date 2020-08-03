package project.manajemenstok.ui.main.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.ListBarangAdapter
import project.manajemenstok.ui.main.viewmodel.BarangViewModel

/**
 * A simple [Fragment] subclass.
 */
class FragmentBarang : Fragment() {

    private lateinit var barangViewModel: BarangViewModel
    private lateinit var adapter: ListBarangAdapter
    private lateinit var viewFragmentBarang: View
    private lateinit var rv: RecyclerView

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

        return viewFragmentBarang
    }

    override fun onResume() {
        super.onResume()
        barangViewModel.fetchLiveBarang()
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
    }

    private fun setupViewModel() {
        val is_remote = true
        barangViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(viewFragmentBarang.context,is_remote)
        ).get(BarangViewModel::class.java)
    }

    private fun renderList(barangs: List<Barang>) {
        adapter.setData(barangs)
        adapter.notifyDataSetChanged()
    }


}
