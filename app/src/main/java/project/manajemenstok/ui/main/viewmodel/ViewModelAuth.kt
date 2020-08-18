package project.manajemenstok.ui.main.viewmodel

import android.content.Context
import android.net.Uri
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

    fun registerAkun(akun: Akun, uuid: String): String{
        return authRepository.createAkun(akun, uuid)
    }

//    fun fetchAkun(username: String){
//        authRepository.fetchAkun(username)
//    }

    fun fetchAkubyId(uuid: String){
        authRepository.fetchAkunById(uuid)
    }

    fun getAkun(): MutableLiveData<Resource<Akun>> {
        return authRepository.getAkun()
    }

    fun updateAkun(akun: Akun, uuid: String, email: String): Resource<String> {

        return authRepository.updateAkun(akun, uuid, email)
    }

    fun updatePassword(oldPass: String, newPass: String): Resource<String> {
        return authRepository.updatePassword(oldPass, newPass)
    }

    fun getUploadResult(): MutableLiveData<String> {
        return authRepository.getUploadResult()
    }

    fun uploadImage(imageUri: Uri, path: String){
        authRepository.uploadImage(imageUri, path)
    }


}