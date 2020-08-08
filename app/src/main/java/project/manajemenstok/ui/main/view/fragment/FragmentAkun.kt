package project.manajemenstok.ui.main.view.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_akun.view.*

import project.manajemenstok.R
import project.manajemenstok.ui.main.view.activity.*

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
                val editProfile = Intent(viewFragmentProfile.context, ActivityEditProfile::class.java)
                startActivity(editProfile)
            }

            R.id.lyt_change_paket -> {
                val memberType = Intent(viewFragmentProfile.context, ActivityMemberType::class.java)
                startActivity(memberType)
            }

            R.id.lyt_change_password -> {
                val changePassword = Intent(viewFragmentProfile.context, ActivityChangePassword::class.java)
                startActivity(changePassword)
            }

            R.id.lyt_keuntungan -> {
                val keuntungan = Intent(viewFragmentProfile.context, KeuntunganAplikasiActivity::class.java)
                startActivity(keuntungan)
            }

            R.id.lyt_terms_condition -> {
                val syaratKetentuan = Intent(viewFragmentProfile.context, ActivitySyaratKetentuan::class.java)
                startActivity(syaratKetentuan)
            }

            R.id.lyt_privacy -> {
                val privacy = Intent(viewFragmentProfile.context, ActivityPrivacy::class.java)
                startActivity(privacy)
            }

            R.id.lyt_help ->{
                val help = Intent(viewFragmentProfile.context, ActivityHelp::class.java)
                startActivity(help)
            }
        }
    }


}
