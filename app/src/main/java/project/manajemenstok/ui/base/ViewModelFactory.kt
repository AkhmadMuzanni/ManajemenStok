package project.manajemenstok.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.manajemenstok.ui.main.viewmodel.*
import java.lang.IllegalArgumentException

//class ViewModelFactory(private val apiHelper: RemoteBrangLogic, private val appDb: BarangLogic) : ViewModelProvider.Factory {
class ViewModelFactory(val context : Context, private val is_remote : Boolean) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ViewModelMain::class.java)){
            return ViewModelMain(context, is_remote) as T
        } else if(modelClass.isAssignableFrom(ViewModelPembelian::class.java)){
            return ViewModelPembelian(context, is_remote) as T
        } else if(modelClass.isAssignableFrom(ViewModelPenjualan::class.java)){
            return ViewModelPenjualan(context, is_remote) as T
        } else if(modelClass.isAssignableFrom(ViewModelRiwayat::class.java)){
            return ViewModelRiwayat(context, is_remote) as T
        } else if(modelClass.isAssignableFrom(ViewModelBarang::class.java)){
            return ViewModelBarang(context, is_remote) as T
        } else if(modelClass.isAssignableFrom(ViewModelKategori::class.java)){
            return ViewModelKategori(context, is_remote) as T
        } else if(modelClass.isAssignableFrom(ViewModelAuth::class.java)){
            return ViewModelAuth(context, is_remote) as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }

//    BarangRepository(apiHelper, appDb)
}