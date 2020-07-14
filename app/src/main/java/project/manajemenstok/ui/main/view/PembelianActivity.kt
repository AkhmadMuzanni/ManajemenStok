package project.manajemenstok.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_pembelian.*
import project.manajemenstok.R
import project.manajemenstok.ui.main.adapter.PembelianAdapter

class PembelianActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembelian)

        val fragmentAdapter = PembelianAdapter(supportFragmentManager)
        val tes : ViewPager = findViewById(R.id.viewpager_main)
        tes.adapter = fragmentAdapter

        val tabs : TabLayout = findViewById(R.id.tabs_main)

        tabs.setupWithViewPager(tes)
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
