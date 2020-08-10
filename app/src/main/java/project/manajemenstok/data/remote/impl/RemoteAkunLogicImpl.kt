package project.manajemenstok.data.remote.impl

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.manajemenstok.data.model.Akun
import project.manajemenstok.data.remote.logic.RemoteAkunLogic
import project.manajemenstok.utils.Resource

class RemoteAkunLogicImpl :
    RemoteAkunLogic {

    private var dataAkun = MutableLiveData<Resource<Akun>>()

    override fun createAkun(akun: Akun): String {
        val dbAkun = Firebase.database.getReference("akun")
        val key = dbAkun.push().key!!
        akun.akun_id = key
        dbAkun.child(key).setValue(akun)
        return key
    }

    override fun getAkun(): MutableLiveData<Resource<Akun>> {
        return dataAkun
    }

    override fun fetchAkun(username: String) {
        val dbAkun = Firebase.database.getReference("akun")
        dbAkun.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    val tempAkun = it.getValue<Akun>(Akun::class.java)!!
                    if(tempAkun.nama == username){
                        setAkun(tempAkun)
                    }
                }

            }
        })
    }

    override fun setAkun(akun: Akun) {
        dataAkun.postValue(Resource.success(akun))
    }

}