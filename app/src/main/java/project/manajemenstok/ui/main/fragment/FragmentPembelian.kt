package project.manajemenstok.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_pembelian.view.*
import project.manajemenstok.R
import project.manajemenstok.ui.main.view.InputBarangActivity

class FragmentPembelian : Fragment(), View.OnClickListener{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewPembelian =  inflater!!.inflate(R.layout.fragment_pembelian, container, false)
        viewPembelian.input_barang_pembelian.setOnClickListener(this)
        viewPembelian.input_barang_pembelian.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                val inputBarangIntent =  Intent(context, InputBarangActivity::class.java)
                startActivity(inputBarangIntent)
            }
        }

        return viewPembelian
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.input_barang_pembelian->{
//                Toast.makeText(v.context, "Ini adalah contoh Toast di Android", Toast.LENGTH_LONG).show()
                val inputBarangIntent =  Intent(this.context, InputBarangActivity::class.java)
                startActivity(inputBarangIntent)
            }
        }
    }

}