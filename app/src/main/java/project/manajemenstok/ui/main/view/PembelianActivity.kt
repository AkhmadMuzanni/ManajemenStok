package project.manajemenstok.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.jakewharton.threetenabp.AndroidThreeTen
import project.manajemenstok.R
import project.manajemenstok.ui.main.adapter.PembelianAdapter

class PembelianActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembelian)

        AndroidThreeTen.init(this)

        val fragmentAdapter = PembelianAdapter(supportFragmentManager)

        val viewPagerPembelian : ViewPager = findViewById(R.id.viewpager_main)
        val tabs : TabLayout = findViewById(R.id.tabs_main)
        val iconBack : ImageView = findViewById(R.id.icon_back)

        viewPagerPembelian.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewPagerPembelian)

        iconBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.icon_back->{
                finish()
            }
        }
    }
}
