package project.manajemenstok.ui.main.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import project.manajemenstok.R

class BarangActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barang)

        val navigationBeranda: BottomNavigationItemView = findViewById(R.id.navigation_beranda)
        navigationBeranda.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.navigation_beranda->{
                val berandaIntent =  Intent(this@BarangActivity, MainActivity::class.java)
                startActivity(berandaIntent)
            }
        }
    }
}
