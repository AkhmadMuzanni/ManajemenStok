package project.manajemenstok.ui.main.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.main.adapter.InputBarangAdapter

class InputBarangActivity : AppCompatActivity(), View.OnClickListener {

    private var barangUsed = ArrayList<Barang>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_barang)

        if(intent.getBundleExtra("dataPembelian") != null){
            barangUsed = intent.getBundleExtra("dataPembelian").getSerializable("dataBarangUsed") as ArrayList<Barang>
        }

        val fragmentAdapter = InputBarangAdapter(supportFragmentManager)

        val viewPagerInputBarang : ViewPager = findViewById(R.id.viewpager_main)
        val tabs : TabLayout = findViewById(R.id.tabs_main)
        val iconBack : ImageView = findViewById(R.id.icon_back)

        viewPagerInputBarang.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewPagerInputBarang)

        iconBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.icon_back->{
//                Toast.makeText(applicationContext, "Kembali", Toast.LENGTH_LONG).show()
                val inputBarangIntent = Intent()
                val inputBarangBundle = Bundle()
                inputBarangIntent.putExtra("bundle",inputBarangBundle)
                setResult(Activity.RESULT_CANCELED, inputBarangIntent)
                finish()
            }
        }
    }

    fun getBarangUsed(): ArrayList<Barang>{
        return barangUsed
    }
}
