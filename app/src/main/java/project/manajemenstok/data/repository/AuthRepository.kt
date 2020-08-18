package project.manajemenstok.data.repository


import android.net.Uri
import androidx.lifecycle.MutableLiveData
import project.manajemenstok.data.model.Akun
import project.manajemenstok.data.remote.logic.RemoteAkunLogic
import project.manajemenstok.utils.Resource


class AuthRepository(private val remoteAkun: RemoteAkunLogic) {

    fun createAkun(akun: Akun, uuid: String): String {
        return remoteAkun.createAkun(akun, uuid)
    }

    fun fetchAkun(username: String){
        remoteAkun.fetchAkun(username)
    }

    fun getAkun(): MutableLiveData<Resource<Akun>> {
        return remoteAkun.getAkun()
    }

    fun fetchAkunById(uuid: String){
        return remoteAkun.fetchAkunById(uuid)
    }

    fun updateAkun(akun: Akun, uuid: String, email: String): Resource<String> {
        return remoteAkun.updateAkun(akun, uuid, email)
    }

    fun updatePassword(oldPass: String, newPass: String): Resource<String> {
        return remoteAkun.updatePassword(oldPass, newPass)
    }

    fun uploadImage(imageUri: Uri, path: String){
        remoteAkun.uploadImage(imageUri, path)
    }

    fun getUploadResult(): MutableLiveData<String>{
        return remoteAkun.getImageUrl()
    }

}