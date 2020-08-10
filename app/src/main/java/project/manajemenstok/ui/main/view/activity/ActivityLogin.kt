package project.manajemenstok.ui.main.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import project.manajemenstok.R
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.viewmodel.ViewModelAuth
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Status

class ActivityLogin : AppCompatActivity(), View.OnClickListener{

    private lateinit var authViewModel: ViewModelAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.database.setPersistenceEnabled(true)
        setContentView(R.layout.activity_login)

        setupViewModel()

        authViewModel.getAkun().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { akun ->
                        Constants.USER_ID = akun.akun_id
                        val intent = Intent(this, ActivityMain::class.java)
                        startActivity(intent)
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    //Handle Error
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        btn_login.setOnClickListener(this)
        btn_signup.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_login->{
                val username = input_username.text.toString()
                authViewModel.fetchAkun(username)
            }
            R.id.btn_signup->{
                val intent = Intent(this, ActivitySignup::class.java)
                startActivity(intent)
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
