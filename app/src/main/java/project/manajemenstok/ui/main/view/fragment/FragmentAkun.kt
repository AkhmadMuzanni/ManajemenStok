package project.manajemenstok.ui.main.view.fragment


import android.content.Intent
import android.os.Bundle
import android.os.TokenWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_akun.*
import kotlinx.android.synthetic.main.fragment_akun.view.*

import project.manajemenstok.R
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.view.activity.*
import project.manajemenstok.ui.main.viewmodel.ViewModelAuth

/**
 * A simple [Fragment] subclass.
 */
class FragmentAkun : Fragment(), View.OnClickListener {

    private lateinit var viewFragmentProfile: View
    private lateinit var auth: FirebaseAuth
    private lateinit var authViewModel: ViewModelAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragmentProfile = inflater.inflate(R.layout.fragment_akun, container, false)
        viewFragmentProfile.lyt_change_profile.setOnClickListener(this)
        viewFragmentProfile.lyt_change_paket.setOnClickListener(this)
        viewFragmentProfile.lyt_change_password.setOnClickListener(this)
        viewFragmentProfile.lyt_keuntungan.setOnClickListener(this)
        viewFragmentProfile.lyt_terms_condition.setOnClickListener(this)
        viewFragmentProfile.lyt_privacy.setOnClickListener(this)
        viewFragmentProfile.lyt_help.setOnClickListener(this)
        viewFragmentProfile.btn_logout.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()
        setupViewModel()
        setupObserver()
        authViewModel.fetchAkubyId(auth.currentUser?.uid.toString())


        return viewFragmentProfile
    }

    private fun setupViewModel() {
        val is_remote = true
        authViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(viewFragmentProfile.context,is_remote)
        ).get(ViewModelAuth::class.java)
    }

    private fun setupObserver() {
        authViewModel.getAkun().observe(this, Observer {
           it.data?.let { akun ->
               viewFragmentProfile.tv_profile_name.text = akun.nama
               viewFragmentProfile.tv_profile_email.text = akun.email
           }
        })
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
                val keuntungan = Intent(viewFragmentProfile.context, ActivityKeuntunganAplikasi::class.java)
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
            R.id.btn_logout->{
                AuthUI.getInstance().signOut(viewFragmentProfile.context)
                    .addOnCompleteListener{
                        activity?.finish()
                    }
                    .addOnFailureListener{
                        e-> Toast.makeText(viewFragmentProfile.context, e.message, Toast.LENGTH_SHORT).show()
                    }

            }
        }
    }


}
