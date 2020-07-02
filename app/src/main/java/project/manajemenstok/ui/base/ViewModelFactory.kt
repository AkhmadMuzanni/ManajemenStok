package project.manajemenstok.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.manajemenstok.data.remote.ApiHelper
import project.manajemenstok.data.local.DatabaseHelper
import project.manajemenstok.data.repository.MainRepository
import project.manajemenstok.ui.main.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiHelper: ApiHelper, private val appDatabase: DatabaseHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(MainRepository(apiHelper, appDatabase)) as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }
}