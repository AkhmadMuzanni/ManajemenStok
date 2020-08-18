package project.manajemenstok.ui.main.view.fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.fragment_akun.view.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

import project.manajemenstok.R
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.view.activity.*
import project.manajemenstok.ui.main.viewmodel.ViewModelAuth
import project.manajemenstok.utils.Constants

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
        viewFragmentProfile.image_view_akun.setOnClickListener(this)

        AndroidThreeTen.init(viewFragmentProfile.context)
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
               Glide.with(viewFragmentProfile.image_view_akun.context).load(akun.foto).into(viewFragmentProfile.image_view_akun)
               viewFragmentProfile.tv_profile_name.text = akun.nama
               viewFragmentProfile.tv_profile_email.text = akun.email
           }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            Constants.RequestCodeIntent.GET_IMAGE->{
                if(resultCode == Activity.RESULT_OK){
                    val imageUri = data?.data
                    authViewModel.getUploadResult().observe(this, Observer {
                        val dateNow = LocalDateTime.now()
                        val dateFormat = dateNow.format(DateTimeFormatter.ofPattern("dd MMM yyy hh:mm:ss"))
                        var akun = Constants.CONSAKUN
                        akun.foto = it
                        akun.dtm_upd = dateFormat.toString()
                        authViewModel.updateAkun(akun, Constants.CONSAKUN.akun_id, Constants.CONSAKUN.email)
                        Glide.with(viewFragmentProfile.context).load(it).into(viewFragmentProfile.image_view_akun)
                        Toast.makeText(viewFragmentProfile.context, "Foto Barang Berhasil Diubah", Toast.LENGTH_LONG).show()
                    })

                    authViewModel.uploadImage(imageUri!!, Constants.CONSAKUN.akun_id + resources.getString(R.string.extensionImage))
                }
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.image_view_akun->{
                val imageInten = Intent()
                imageInten.setType("image/*")
                imageInten.setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(imageInten, Constants.RequestCodeIntent.GET_IMAGE)
            }

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
