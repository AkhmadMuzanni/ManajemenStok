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
import kotlinx.android.synthetic.main.fragment_barang.*
import kotlinx.android.synthetic.main.fragment_beranda.*

import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.MainAdapter
import project.manajemenstok.ui.main.viewmodel.MainViewModel
import project.manajemenstok.utils.Status

/**
 * A simple [Fragment] subclass.
 */
class FragmentBarang : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var viewFragmentBarang: View
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragmentBarang = inflater.inflate(R.layout.fragment_barang, container, false)

        setupUI()
        setupViewModel()
        setupObserver()
        return viewFragmentBarang
    }

    private fun setupUI(){
        rv = viewFragmentBarang.findViewById(R.id.rv_beranda)
        rv.layoutManager = LinearLayoutManager(viewFragmentBarang.context)
        adapter = MainAdapter(arrayListOf())
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
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(viewFragmentBarang.context,is_remote)
        ).get(MainViewModel::class.java)
    }

    private fun setupObserver() {
        mainViewModel.getBarangs().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    rv.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    rv.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(barangs: List<Barang>) {
        adapter.addData(barangs)
        adapter.notifyDataSetChanged()
    }


}
