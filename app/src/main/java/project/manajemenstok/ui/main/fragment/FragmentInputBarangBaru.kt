package project.manajemenstok.ui.main.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_input_barang_baru.*
import kotlinx.android.synthetic.main.fragment_input_barang_baru.view.*
import project.manajemenstok.R
import project.manajemenstok.ui.main.view.InputBarangActivity
import project.manajemenstok.ui.main.view.PembelianActivity


class FragmentInputBarangBaru : Fragment(), View.OnClickListener{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val viewInputBarangBaru = inflater!!.inflate(R.layout.fragment_input_barang_baru, container, false)
        viewInputBarangBaru.btn_simpan.setOnClickListener(this)
        return viewInputBarangBaru
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btn_simpan->{
                val inputBarangIntent = Intent()
                val inputBarangBundle = Bundle()
                inputBarangBundle.putInt("id", 0)
                inputBarangBundle.putString("namaBarang", this.input_barang_pembelian.text.toString())
                inputBarangBundle.putString("uuid", "")
                inputBarangIntent.putExtra("bundle",inputBarangBundle)
                activity?.setResult(RESULT_OK, inputBarangIntent)
                activity?.finish()
            }
        }
    }

}