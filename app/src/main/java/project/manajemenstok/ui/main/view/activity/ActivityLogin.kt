package project.manajemenstok.ui.main.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Tag
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivityForResult
import project.manajemenstok.R
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.viewmodel.ViewModelAuth
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Status

class ActivityLogin : AppCompatActivity(), View.OnClickListener{

    private lateinit var authViewModel: ViewModelAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Firebase.database.setPersistenceEnabled(true)
        auth = FirebaseAuth.getInstance()
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

    override fun onResume() {
        super.onResume()

        input_username.setText("")
        input_password.setText("")
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_login->{
                val username = input_username.text.toString()
                val password = input_password.text.toString()
//                authViewModel.fetchAkun(username)

                doSignIn(username,password)
            }
            R.id.btn_signup->{
                val intent = Intent(this, ActivitySignup::class.java)
                startActivity(intent)
//                tombol keluar enable
            }
        }
    }

    private fun doSignIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("success", "signInWithEmail:success")
//                    val user = auth.currentUser
                    val intent = Intent(this, ActivityMain::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
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

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Constants.USER_ID = auth.currentUser?.uid.toString()
            val intent = Intent(this, ActivityMain::class.java)
            startActivity(intent)
        }
    }
}
