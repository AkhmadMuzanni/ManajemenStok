package project.manajemenstok.ui.main.fragment


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
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.fragment_beranda.view.*

import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.MainAdapter
import project.manajemenstok.ui.main.view.PembelianActivity
import project.manajemenstok.ui.main.viewmodel.MainViewModel
import project.manajemenstok.utils.Status

/**
 * A simple [Fragment] subclass.
 */
class FragmentBeranda : Fragment(), View.OnClickListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var viewFragmentBeranda: View
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewFragmentBeranda = inflater.inflate(R.layout.fragment_beranda, container, false)
        viewFragmentBeranda.btn_pembelian.setOnClickListener(this)
        setupUI()
        setupViewModel()
        setupObserver()
        return viewFragmentBeranda
    }

    private fun setupUI(){
        rv = viewFragmentBeranda.findViewById(R.id.rv_riwayat_transaksi)
        rv.layoutManager = LinearLayoutManager(viewFragmentBeranda.context)
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
            ViewModelFactory(viewFragmentBeranda.context,is_remote)
        ).get(MainViewModel::class.java)
    }

    private fun setupObserver() {
        mainViewModel.getBarangs().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBarBeranda.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    rv.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBarBeranda.visibility = View.VISIBLE
                    rv.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBarBeranda.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(barangs: List<Barang>) {
        adapter.addData(barangs)
        adapter.notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btn_pembelian->{
//                Toast.makeText(applicationContext, "Ini adalah contoh Toast di Android", Toast.LENGTH_LONG).show()
                val pembelianIntent = Intent(this.context, PembelianActivity::class.java)
                startActivity(pembelianIntent)
            }
        }
    }


}