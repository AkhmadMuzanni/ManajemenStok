package project.manajemenstok.ui.main.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_signup.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Akun
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.viewmodel.ViewModelAuth

class ActivitySignup : AppCompatActivity(), View.OnClickListener{

    private lateinit var authViewModel: ViewModelAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setupViewModel()

        btn_login.setOnClickListener(this)
        btn_signup.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_signup->{
                var newAkun = Akun()
                newAkun.nama = input_username.text.toString()
                newAkun.nama_toko = "Toko " + newAkun.nama
                authViewModel.registerAkun(newAkun)
                Toast.makeText(applicationContext, "Daftar berhasil", Toast.LENGTH_LONG).show()
            }
            R.id.btn_login->{
                finish()
            }
        }
    }

    private fun setupViewModel() {
        val is_remote = true
        authViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(this,is_remote)
        ).get(ViewModelAuth::class.java)
    }
}
