package project.manajemenstok.ui.main.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_login.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Akun
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.viewmodel.ViewModelAuth
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Status

class ActivityEditProfile : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var authViewModel: ViewModelAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        auth = FirebaseAuth.getInstance()
        setupViewModel()
        setupObserver()
        authViewModel.fetchAkubyId(auth.currentUser?.uid.toString())
        btn_edit_simpan.setOnClickListener(this)
    }

    private fun setupViewModel() {
        val is_remote = true
        authViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(this,is_remote)
        ).get(ViewModelAuth::class.java)
    }

    private fun setupObserver(){
        authViewModel.getAkun().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { akun ->
                        input_edit_name.setText(akun.nama)
                        input_edit_email.setText(auth.currentUser?.email.toString())
                        input_edit_toko.setText(akun.nama_toko)
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_edit_simpan -> {
                val uuid = auth.currentUser?.uid.toString()
                val username = input_edit_name.text.toString()
                val email = input_edit_email.text.toString()
                val toko = input_edit_toko.text.toString()

                var editAkun = Akun()
                editAkun.akun_id = uuid
                editAkun.email = email
                editAkun.password = Constants.CONSAKUN.password
                editAkun.nama = username
                editAkun.nama_toko = toko
                editAkun.paket = Constants.CONSAKUN.paket

                when(authViewModel.updateAkun(editAkun, uuid, email).status){
                    Status.SUCCESS -> {
                        Toast.makeText(this, "Data Berhasil Diubah",  Toast.LENGTH_LONG).show()
                        Constants.CONSAKUN.nama = editAkun.nama
                        Constants.CONSAKUN.email = editAkun.email
                        Constants.CONSAKUN.nama_toko = editAkun.nama_toko
                    }

                    Status.ERROR -> {
                        Toast.makeText(this,authViewModel.updateAkun(editAkun, uuid, email).message,  Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }
}
