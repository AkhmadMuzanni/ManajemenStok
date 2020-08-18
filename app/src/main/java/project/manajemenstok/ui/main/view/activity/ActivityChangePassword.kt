package project.manajemenstok.ui.main.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_change_password.*
import project.manajemenstok.R
import project.manajemenstok.ui.base.ViewModelFactory
import project.manajemenstok.ui.main.viewmodel.ViewModelAuth
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Status

class ActivityChangePassword : AppCompatActivity(), View.OnClickListener {

    private lateinit var authViewModel: ViewModelAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        AndroidThreeTen.init(this)
        setupViewModel()

        btn_save_chpass.setOnClickListener(this)
    }

    private fun setupViewModel() {
        val is_remote = true
        authViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(this,is_remote)
        ).get(ViewModelAuth::class.java)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_save_chpass ->{
                val oldPass = et_input_chpass_old.text.toString()
                val newPass = et_input_chpass_new.text.toString()
                val confirm = et_input_chpass_newconfirm.text.toString()

                if (newPass.equals(confirm)){
                    if (newPass.length<6 && confirm.length<6){
                        Toast.makeText(this, "Password Kurang Dari 6 Karakter", Toast.LENGTH_SHORT).show()
                    }else if(oldPass!=Constants.CONSAKUN.password ){
                        Toast.makeText(this, "Password Lama Tidak Sesuai ", Toast.LENGTH_LONG).show()
                    }else{
                        when(authViewModel.updatePassword(oldPass,newPass).status){
                            Status.SUCCESS -> {
                                Toast.makeText(this, "Password Berhasil Diubah", Toast.LENGTH_SHORT).show()
                                Constants.CONSAKUN.password = newPass
                            }

                            Status.ERROR -> {
                                Toast.makeText(this, authViewModel.updatePassword(oldPass,newPass).message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }else{
                    Toast.makeText(this, "Confirmasi Password Tidak Sama", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
