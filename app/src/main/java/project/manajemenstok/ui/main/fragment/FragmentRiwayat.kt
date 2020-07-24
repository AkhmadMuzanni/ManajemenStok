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
import kotlinx.android.synthetic.main.fragment_barang.*
import kotlinx.android.synthetic.main.fragment_riwayat.*

import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.model.Pembelian
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.OnRiwayatItemClickListener
import project.manajemenstok.ui.main.adapter.RiwayatAdapter
import project.manajemenstok.ui.main.view.DetailRiwayatActivity
import project.manajemenstok.ui.main.viewmodel.PembelianViewModel
import project.manajemenstok.utils.Status

/**
 * A simple [Fragment] subclass.
 */
class FragmentRiwayat : Fragment(), OnRiwayatItemClickListener {

    private lateinit var pembelianViewModel: PembelianViewModel
    private lateinit var adapter: RiwayatAdapter
    private lateinit var viewFragmentRiwayat: View
    private lateinit var rv: RecyclerView
//    private lateinit var temp: List<Barang>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragmentRiwayat = inflater.inflate(R.layout.fragment_riwayat, container, false)
        setupUI()
        setupViewModel()
        setupObserver()
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
        pembelianViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(viewFragmentRiwayat.context,is_remote)
        ).get(PembelianViewModel::class.java)
    }

    private fun setupObserver() {
        pembelianViewModel.getPembelian().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBarRiwayat.visibility = View.GONE
                    it.data?.let { pembelians -> renderList(pembelians) }
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

    private fun renderList(pembelians: List<Pembelian>) {
        adapter.addData(pembelians)
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(item: Pembelian, position: Int) {
        val historyTransaction = Intent(viewFragmentRiwayat.context, DetailRiwayatActivity::class.java)
        val historyTransactionBundle = Bundle()
        historyTransactionBundle.putSerializable("HISTORYTRANSACTION", item)
        historyTransaction.putExtra("historyTransaction", historyTransactionBundle)
        startActivity(historyTransaction)
    }

}
