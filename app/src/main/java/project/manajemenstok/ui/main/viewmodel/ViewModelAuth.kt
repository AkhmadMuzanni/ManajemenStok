package project.manajemenstok.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.manajemenstok.data.model.Akun
import project.manajemenstok.data.remote.impl.RemoteAkunLogicImpl
import project.manajemenstok.data.repository.AuthRepository
import project.manajemenstok.utils.Resource

class ViewModelAuth (val context : Context, private val is_remote : Boolean) : ViewModel() {

    private val authRepository = AuthRepository(
        RemoteAkunLogicImpl()
    )

    fun registerAkun(akun: Akun): String{
        return authRepository.createAkun(akun)
    }

    fun fetchAkun(username: String){
        authRepository.fetchAkun(username)
    }

    fun getAkun(): MutableLiveData<Resource<Akun>> {
        return authRepository.getAkun()
    }


}