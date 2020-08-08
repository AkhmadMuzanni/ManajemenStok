package project.manajemenstok.ui.main.view.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.jakewharton.threetenabp.AndroidThreeTen
import project.manajemenstok.R
import project.manajemenstok.ui.main.adapter.PembelianAdapter
import project.manajemenstok.ui.main.view.fragment.FragmentPembelian

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
                val fragment = supportFragmentManager.findFragmentById(R.id.viewpager_main) as FragmentPembelian
                var builder = AlertDialog.Builder(this, R.style.CustomDialogTheme)
                builder.setTitle("Konfirmasi")
                builder.setMessage("Simpan Sebagai Draft?")

                builder.setPositiveButton("KEMBALI") { dialog, which ->
                }

                builder.setNegativeButton("HAPUS") { dialog, which ->
                    fragment.destroyView(false)
                    finish()
                }

                builder.setNeutralButton("SIMPAN DRAFT") { dialog, which ->
                    fragment.destroyView(true)
                    finish()
                }
                builder.show()
            }
        }
    }

    override fun onBackPressed() {
        findViewById<ImageView>(R.id.icon_back).performClick()
    }
}
