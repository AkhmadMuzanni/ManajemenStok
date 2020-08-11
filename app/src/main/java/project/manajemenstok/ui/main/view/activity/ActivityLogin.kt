package project.manajemenstok.ui.main.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivityForResult
import project.manajemenstok.R
import java.util.*

class ActivityLogin : AppCompatActivity(), View.OnClickListener{

//    lateinit var providers : List<AuthUI.IdpConfig>

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.database.setPersistenceEnabled(true)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener(this)

//        providers = Arrays.asList<AuthUI.IdpConfig>(
//            AuthUI.IdpConfig.EmailBuilder().build(),
//            AuthUI.IdpConfig.GoogleBuilder().build()
//        )

//        showSignInOptions()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 12345){
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK){
                val intent = Intent(this, ActivityMain::class.java)
                startActivity(intent)
//                tombol keluar enable
            }else{
                Toast.makeText(this, ""+response!!.error!!.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun showSignInOptions(){
//        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
//            .setAvailableProviders(providers)
//            .setTheme(R.style.AppTheme)
//            .build(), 12345) //request code bebas
//    }

    override fun onClick(v: View) {
        when(v.id){
//            R.id.btn_login->{
//                val intent = Intent(this, ActivityMain::class.java)
//                startActivity(intent)
//            }
        }
    }
}
