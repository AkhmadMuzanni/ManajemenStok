package project.manajemenstok.data.remote.logic

import androidx.lifecycle.MutableLiveData
import project.manajemenstok.data.model.Akun
import project.manajemenstok.utils.Resource

interface RemoteAkunLogic {
    fun createAkun(akun: Akun): String
    fun getAkun(): MutableLiveData<Resource<Akun>>
    fun fetchAkun()
    fun setAkun(akun: Akun)
}