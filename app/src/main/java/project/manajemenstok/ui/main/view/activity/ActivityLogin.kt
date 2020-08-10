package project.manajemenstok.ui.main.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import project.manajemenstok.R
import project.manajemenstok.utils.Constants

class ActivityLogin : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.database.setPersistenceEnabled(true)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener(this)
        btn_signup.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_login->{
                Constants.USER_ID = input_username.text.toString()
                val intent = Intent(this, ActivityMain::class.java)
                startActivity(intent)
            }
            R.id.btn_signup->{
                val intent = Intent(this, ActivitySignup::class.java)
                startActivity(intent)
            }
        }
    }
}
