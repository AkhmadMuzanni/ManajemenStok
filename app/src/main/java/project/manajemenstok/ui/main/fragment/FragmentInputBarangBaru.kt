package project.manajemenstok.ui.main.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_input_barang_baru.*
import kotlinx.android.synthetic.main.fragment_input_barang_baru.kategori_spinner
import kotlinx.android.synthetic.main.fragment_input_barang_baru.view.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.view.DetailKategoriActivity
import project.manajemenstok.ui.main.viewmodel.PembelianViewModel
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Status


class FragmentInputBarangBaru : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener{
    private lateinit var pembelianViewModel: PembelianViewModel
    private lateinit var listKategori: ArrayList<Kategori>
    private lateinit var arrayAdapter: ArrayAdapter<Kategori>
    private var uuidNewKategori = "nonKategori"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val viewInputBarangBaru = inflater.inflate(R.layout.fragment_input_barang_baru, container, false)

        setupViewModel()

        pembelianViewModel.getKategori().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { kategori ->
                        listKategori = kategori

                        if(listKategori[0].uuid != "newKategori"){
                            listKategori.add(0,Kategori(uuid="newKategori", nama="Buat Kategori"))
                        }

                        arrayAdapter = ArrayAdapter<Kategori>(context!!, R.layout.support_simple_spinner_dropdown_item, listKategori)
                        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                        kategori_spinner.adapter = arrayAdapter

                        for (kategori in listKategori){
                            if(kategori.uuid == uuidNewKategori){
                                kategori_spinner.setSelection(arrayAdapter.getPosition(kategori))
                            }
                        }
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    //Handle Error
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        viewInputBarangBaru.btn_simpan.setOnClickListener(this)
        return viewInputBarangBaru
    }

    override fun onResume() {
        super.onResume()

        pembelianViewModel.fetchKategori()

        kategori_spinner.onItemSelectedListener = this
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btn_simpan->{
                val inputBarangIntent = Intent()
                val inputBarangBundle = Bundle()
                val objKategori = kategori_spinner.selectedItem as Kategori

                inputBarangBundle.putInt("id", 0)
                inputBarangBundle.putString("namaBarang", this.input_barang_pembelian.text.toString())
                inputBarangBundle.putString("uuid", "")
                inputBarangBundle.putString("foto", Constants.defaultImageObject)
                inputBarangBundle.putString("kategori", objKategori.uuid)
                inputBarangIntent.putExtra("bundle",inputBarangBundle)
                activity?.setResult(RESULT_OK, inputBarangIntent)
                activity?.finish()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedKategori = parent?.selectedItem as Kategori

        if(selectedKategori.uuid == "newKategori"){
            val detailKategoriIntent =  Intent(context, DetailKategoriActivity::class.java)
            detailKategoriIntent.putExtra("intentMode", Constants.IntentMode.ADD)
            startActivityForResult(detailKategoriIntent, Constants.RequestCodeIntent.DETAIL_KATEGORI)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.RequestCodeIntent.DETAIL_KATEGORI && data != null && resultCode == RESULT_OK){
            val tempUuidNewKategori = data?.getStringExtra("uuidNewKategori")
            uuidNewKategori = tempUuidNewKategori
        }
    }

    private fun setupViewModel() {
        val is_remote = true
        pembelianViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(context!!,is_remote)
        ).get(PembelianViewModel::class.java)
    }

}