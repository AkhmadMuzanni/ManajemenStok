package project.manajemenstok.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_input_barang_tersimpan.view.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.MainAdapter
import project.manajemenstok.ui.main.viewmodel.MainViewModel
import project.manajemenstok.utils.Status


class FragmentInputBarangTersimpan : Fragment(){
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var viewBarang : View
    private lateinit var rvBarang : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBarang = inflater!!.inflate(R.layout.fragment_input_barang_tersimpan, container, false)
        setupUI()
        setupViewModel()
        setupObserver()
        return viewBarang
//        return inflater!!.inflate(R.layout.fragment_input_barang_tersimpan, container, false)

    }

    private fun setupUI(){
        rvBarang = viewBarang.recyclerView
        rvBarang.layoutManager = LinearLayoutManager(viewBarang.context)
        adapter = MainAdapter(arrayListOf())
        rvBarang.recyclerView.addItemDecoration(
            DividerItemDecoration(
                rvBarang.context,
                (rvBarang.layoutManager as LinearLayoutManager).orientation
            )
        )
        rvBarang.adapter = adapter
    }

    private fun setupViewModel() {
        val is_remote = true
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(viewBarang.context,is_remote)
        ).get(MainViewModel::class.java)
    }

    private fun setupObserver() {
        mainViewModel.getBarangs().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
//                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    rvBarang.visibility = View.VISIBLE
                }
                Status.LOADING -> {
//                    progressBar.visibility = View.VISIBLE
                    rvBarang.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
//                    progressBar.visibility = View.GONE
                    Toast.makeText(viewBarang.context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(barangs: List<Barang>) {
        adapter.addData(barangs)
        adapter.notifyDataSetChanged()
    }

}