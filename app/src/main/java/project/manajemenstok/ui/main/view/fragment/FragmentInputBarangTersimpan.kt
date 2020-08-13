package project.manajemenstok.ui.main.view.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_input_barang_tersimpan.*
import kotlinx.android.synthetic.main.fragment_input_barang_tersimpan.view.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.adapter.InputBarangTersimpanAdapter
import project.manajemenstok.ui.main.view.activity.ActivityInputBarang
import project.manajemenstok.ui.main.viewmodel.ViewModelPembelian
import project.manajemenstok.utils.Helper
import project.manajemenstok.utils.Status


class FragmentInputBarangTersimpan : Fragment(), SearchView.OnQueryTextListener{
    private lateinit var pembelianViewModel: ViewModelPembelian
    private lateinit var adapter: InputBarangTersimpanAdapter
    private lateinit var viewBarang : View
    private lateinit var rvBarang : RecyclerView
    private lateinit var barangUsed : ArrayList<Barang>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBarang = inflater!!.inflate(R.layout.fragment_input_barang_tersimpan, container, false)
        setupUI()
        setupViewModel()

        pembelianViewModel.getUnusedBarang().observe(this, Observer {
            if(it.size == 0){
                recyclerView.visibility = View.GONE
                text_empty_state.visibility = View.VISIBLE
//                sv_barang_tersimpan.visibility = View.GONE
            } else {
                recyclerView.visibility = View.VISIBLE
                text_empty_state.visibility= View.GONE
//                sv_barang_tersimpan.visibility = View.VISIBLE

                renderList(it)
            }
        })

        barangUsed = (activity as ActivityInputBarang).getBarangUsed()

        pembelianViewModel.fetchUnusedBarang(barangUsed)

        return viewBarang
//        return inflater!!.inflate(R.layout.fragment_input_barang_tersimpan, container, false)

    }

    override fun onResume() {
        super.onResume()
        Helper.setFontSizeSearchView(sv_barang_tersimpan, 14f)
        sv_barang_tersimpan.setIconifiedByDefault(false)
        sv_barang_tersimpan.setOnQueryTextListener(this)
    }

    private fun setupUI(){
        val parentActivity = activity?.intent!!.getIntExtra("parent", 0)
        rvBarang = viewBarang.recyclerView
        rvBarang.layoutManager = LinearLayoutManager(viewBarang.context)
        adapter = InputBarangTersimpanAdapter(arrayListOf(), this, parentActivity)
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
        pembelianViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(viewBarang.context,is_remote)
        ).get(ViewModelPembelian::class.java)
    }

    private fun setupObserver() {
        pembelianViewModel.getBarangs().observe(this, Observer {
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
//        val barangUsed = (activity as InputBarangActivity).getBarangUsed()
//        adapter.addData(pembelianViewModel.getUnusedBarang(barangUsed))
        adapter.setData(barangs)
        adapter.notifyDataSetChanged()
    }

    fun sendData(barang: Barang) {
        val inputBarangIntent = Intent()
        val inputBarangBundle = Bundle()
        inputBarangBundle.putInt("id", barang.id)
        inputBarangBundle.putString("namaBarang", barang.namaBarang)
        inputBarangBundle.putString("uuid", barang.uuid)
        inputBarangBundle.putInt("harga", barang.harga)
        inputBarangBundle.putInt("maxQuantity", barang.jumlah)
        inputBarangBundle.putString("foto", barang.foto)
        inputBarangBundle.putString("kategori", barang.kategori)
        inputBarangIntent.putExtra("bundle",inputBarangBundle)
        activity?.setResult(Activity.RESULT_OK, inputBarangIntent)
        activity?.finish()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText == ""){
            pembelianViewModel.fetchUnusedBarang(barangUsed)
        } else {
            pembelianViewModel.fetchUnusedBarang(barangUsed, newText!!.toLowerCase())
        }
        return true
    }

}