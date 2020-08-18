package project.manajemenstok.ui.main.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_signup.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import project.manajemenstok.R
import project.manajemenstok.data.model.Akun
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.viewmodel.ViewModelAuth
import project.manajemenstok.utils.Constants

class ActivitySignup : AppCompatActivity(), View.OnClickListener{

    private lateinit var authViewModel: ViewModelAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth = FirebaseAuth.getInstance()
        AndroidThreeTen.init(this)
        setupViewModel()

        btn_login.setOnClickListener(this)
        btn_signup.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_signup->{
                val dateNow = LocalDateTime.now()
                val dateFormat = dateNow.format(DateTimeFormatter.ofPattern("dd MMM yyy hh:mm:ss"))
                var newAkun = Akun()
                newAkun.email = input_email.text.toString()
                newAkun.password = input_password.text.toString()
                newAkun.nama = input_username.text.toString()
                newAkun.nama_toko = input_toko.text.toString()
                newAkun.paket = "Merkurius"
                newAkun.foto = Constants.defaultImageObject
                newAkun.dtm_crt = dateFormat.toString()
                newAkun.dtm_upd = dateFormat.toString()
                if (newAkun.password.length<6){

                    Toast.makeText(this, "Password Kurang Dari 6 Karakter", Toast.LENGTH_LONG).show()
                }else{
                    auth.createUserWithEmailAndPassword(newAkun.email, newAkun.password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val uuid = task.result?.user?.uid.toString()
                                if (authViewModel.registerAkun(newAkun, uuid.toString()).isNullOrBlank()){
                                    Toast.makeText(this, "Daftar gagal", Toast.LENGTH_LONG).show()
                                }else{
                                    Toast.makeText(this, "Daftar berhasil", Toast.LENGTH_LONG).show()
                                    Constants.CONSAKUN = newAkun
                                }
                            } else {
                                Toast.makeText(this, task.exception.toString(),
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                }

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
