package project.manajemenstok.data.remote.impl

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.manajemenstok.data.model.Akun
import project.manajemenstok.data.remote.logic.RemoteAkunLogic
import project.manajemenstok.utils.Resource

class RemoteAkunLogicImpl :
    RemoteAkunLogic {
    override fun createAkun(akun: Akun): String {
        val dbAkun = Firebase.database.getReference("akun")
        val key = dbAkun.push().key!!
        akun.akun_id = key
        dbAkun.child(key).setValue(akun)
        return key
    }

    override fun getAkun(): MutableLiveData<Resource<Akun>> {
        TODO("Not yet implemented")
    }

    override fun fetchAkun() {
        TODO("Not yet implemented")
    }

    override fun setAkun(akun: Akun) {
        TODO("Not yet implemented")
    }

}