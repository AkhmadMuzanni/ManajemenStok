package project.manajemenstok.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import project.manajemenstok.R


class FragmentInputBarangTersimpan : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_input_barang_tersimpan, container, false)
    }

}