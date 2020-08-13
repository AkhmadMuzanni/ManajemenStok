package project.manajemenstok.data.remote.logic

import androidx.lifecycle.MutableLiveData
import project.manajemenstok.data.model.Akun
import project.manajemenstok.utils.Resource

interface RemoteAkunLogic {
    fun createAkun(akun: Akun, uuid: String): String
    fun getAkun(): MutableLiveData<Resource<Akun>>
    fun fetchAkun(username: String)
    fun setAkun(akun: Akun)
    fun updateAkun(akun: Akun, uuid: String, email: String): Resource<String>
    fun updatePassword(oldPass: String, newPass: String): Resource<String>
    fun fetchAkunById(uuid: String)

}