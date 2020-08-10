package project.manajemenstok.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import project.manajemenstok.data.model.Akun
import project.manajemenstok.data.remote.impl.RemoteAkunLogicImpl
import project.manajemenstok.data.repository.AuthRepository

class ViewModelAuth (val context : Context, private val is_remote : Boolean) : ViewModel() {

    private val authRepository = AuthRepository(
        RemoteAkunLogicImpl()
    )

    fun registerAkun(akun: Akun): String{
        return authRepository.createAkun(akun)
    }


}