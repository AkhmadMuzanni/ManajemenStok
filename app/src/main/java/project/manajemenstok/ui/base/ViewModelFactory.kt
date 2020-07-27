package project.manajemenstok.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.manajemenstok.ui.main.viewmodel.MainViewModel
import project.manajemenstok.ui.main.viewmodel.PembelianViewModel
import project.manajemenstok.ui.main.viewmodel.PenjualanViewModel
import java.lang.IllegalArgumentException

//class ViewModelFactory(private val apiHelper: RemoteBrangLogic, private val appDb: BarangLogic) : ViewModelProvider.Factory {
class ViewModelFactory(val context : Context, private val is_remote : Boolean) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(context, is_remote) as T
        } else if(modelClass.isAssignableFrom(PembelianViewModel::class.java)){
            return PembelianViewModel(context, is_remote) as T
        } else if(modelClass.isAssignableFrom(PenjualanViewModel::class.java)){
            return PenjualanViewModel(context, is_remote) as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }

//    BarangRepository(apiHelper, appDb)
}