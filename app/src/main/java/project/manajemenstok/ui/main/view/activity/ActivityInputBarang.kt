package project.manajemenstok.ui.main.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_input_barang.*
import project.manajemenstok.R
import project.manajemenstok.data.model.Barang
import project.manajemenstok.ui.main.adapter.InputBarangAdapter

class ActivityInputBarang : AppCompatActivity(), View.OnClickListener {

    private var barangUsed = ArrayList<Barang>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_barang)

        if(intent.getBundleExtra("dataTransaksi") != null){
            barangUsed = intent.getBundleExtra("dataTransaksi").getSerializable("dataBarangUsed") as ArrayList<Barang>
        }

        val parentActivity = intent.getIntExtra("parent", 0)

        if(parentActivity == 0){
            txt_header.text = "Pembelian"
        } else {
            txt_header.text = "Penjualan"
        }

        val fragmentAdapter = InputBarangAdapter(supportFragmentManager, parentActivity)

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
