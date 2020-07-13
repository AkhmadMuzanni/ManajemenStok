package project.manajemenstok.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import project.manajemenstok.R

class PembelianActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembelian)
    }

    override fun onClick(v: View) {
//        when (v.id){
//            R.id.navigation_beranda->{
//                val berandaIntent =  Intent(this@PembelianActivity, MainActivity::class.java)
//                startActivity(berandaIntent)
//            }
//        }
    }
}
