package project.manajemenstok.data.repository


import androidx.lifecycle.MutableLiveData
import project.manajemenstok.data.model.Akun
import project.manajemenstok.data.remote.logic.RemoteAkunLogic
import project.manajemenstok.utils.Resource


class AuthRepository(private val remoteAkun: RemoteAkunLogic) {

    fun createAkun(akun: Akun): String {
        return remoteAkun.createAkun(akun)
    }

    fun fetchAkun(username: String){
        remoteAkun.fetchAkun(username)
    }

    fun getAkun(): MutableLiveData<Resource<Akun>> {
        return remoteAkun.getAkun()
    }

}