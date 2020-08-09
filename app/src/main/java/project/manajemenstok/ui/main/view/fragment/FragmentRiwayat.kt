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
import kotlinx.android.synthetic.main.fragment_riwayat.*

import project.manajemenstok.R
import project.manajemenstok.data.model.TransaksiData
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.OnRiwayatItemClickListener
import project.manajemenstok.ui.main.adapter.RiwayatAdapter
import project.manajemenstok.ui.main.view.activity.ActivityDetailRiwayat
import project.manajemenstok.ui.main.viewmodel.RiwayatViewModel
import project.manajemenstok.utils.Status

/**
 * A simple [Fragment] subclass.
 */
class FragmentRiwayat : Fragment(), OnRiwayatItemClickListener {

    private lateinit var riwayatViewModel: RiwayatViewModel
    private lateinit var adapter: RiwayatAdapter
    private lateinit var viewFragmentRiwayat: View
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragmentRiwayat = inflater.inflate(R.layout.fragment_riwayat, container, false)
        setupUI()
        setupViewModel()
        setupObserver()
        riwayatViewModel.fetchTransaksiRepository()
        return viewFragmentRiwayat
    }

    private fun setupUI(){
        rv = viewFragmentRiwayat.findViewById(R.id.rv_riwayat)
        rv.layoutManager = LinearLayoutManager(viewFragmentRiwayat.context)
        adapter = RiwayatAdapter(arrayListOf(), this)
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
        riwayatViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(viewFragmentRiwayat.context,is_remote)
        ).get(RiwayatViewModel::class.java)
    }

    private fun setupObserver() {
        riwayatViewModel.getTransaksi().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBarRiwayat.visibility = View.GONE
                    it.data?.let {
                            transaksi -> renderList(transaksi)
                    }
                    rv.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBarRiwayat.visibility = View.VISIBLE
                    rv.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBarRiwayat.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(transaksiList: List<TransaksiData>) {
        adapter.setData(transaksiList.reversed())
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(item: TransaksiData, position: Int) {
        val historyTransaction = Intent(viewFragmentRiwayat.context, ActivityDetailRiwayat::class.java)
        val historyTransactionBundle = Bundle()
        historyTransactionBundle.putString("idTransaksi", item.uuid)
        historyTransactionBundle.putInt("totalTransaksi", item.totalTransaksi)
        historyTransactionBundle.putInt("totalOngkir", item.ongkir)
        historyTransaction.putExtra("historyTransaction", historyTransactionBundle)
        startActivity(historyTransaction)
    }

}
