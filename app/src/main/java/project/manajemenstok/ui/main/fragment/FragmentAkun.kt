package project.manajemenstok.ui.main.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.fragment_akun.*
import kotlinx.android.synthetic.main.fragment_akun.view.*

import project.manajemenstok.R
import project.manajemenstok.ui.main.view.KeuntunganAplikasiActivity

/**
 * A simple [Fragment] subclass.
 */
class FragmentAkun : Fragment(), View.OnClickListener {

    private lateinit var viewFragmentProfile: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewFragmentProfile = inflater.inflate(R.layout.fragment_akun, container, false)
        viewFragmentProfile.lyt_change_profile.setOnClickListener(this)
        viewFragmentProfile.lyt_change_paket.setOnClickListener(this)
        viewFragmentProfile.lyt_change_password.setOnClickListener(this)
        viewFragmentProfile.lyt_keuntungan.setOnClickListener(this)
        viewFragmentProfile.lyt_terms_condition.setOnClickListener(this)
        viewFragmentProfile.lyt_privacy.setOnClickListener(this)
        viewFragmentProfile.lyt_help.setOnClickListener(this)
        return viewFragmentProfile
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.lyt_change_profile -> {
                Toast.makeText(viewFragmentProfile.context, "Ubah Profile", Toast.LENGTH_SHORT).show()
            }

            R.id.lyt_change_paket -> {
                Toast.makeText(viewFragmentProfile.context, "Ubah Paket", Toast.LENGTH_SHORT).show()
            }

            R.id.lyt_change_password -> {
                Toast.makeText(viewFragmentProfile.context, "Ubah Password", Toast.LENGTH_SHORT).show()
            }

            R.id.lyt_keuntungan -> {
                val keuntungan = Intent(viewFragmentProfile.context, KeuntunganAplikasiActivity::class.java)
                startActivity(keuntungan)
            }

            R.id.lyt_terms_condition -> {
                Toast.makeText(viewFragmentProfile.context, "Syarat Dan Ketentuan", Toast.LENGTH_SHORT).show()
            }

            R.id.lyt_privacy -> {
                Toast.makeText(viewFragmentProfile.context, "Kebijakan Privasi", Toast.LENGTH_SHORT).show()
            }

            R.id.lyt_help ->{
                Toast.makeText(viewFragmentProfile.context, "Bantuan", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
