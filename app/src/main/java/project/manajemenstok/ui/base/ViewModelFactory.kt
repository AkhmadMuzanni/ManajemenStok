package project.manajemenstok.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.manajemenstok.data.api.ApiHelper
import project.manajemenstok.data.repository.MainRepository
import project.manajemenstok.ui.main.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }
}