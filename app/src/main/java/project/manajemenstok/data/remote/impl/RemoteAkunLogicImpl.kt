package project.manajemenstok.data.remote.impl

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.manajemenstok.data.model.Akun
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.data.remote.logic.RemoteAkunLogic
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Resource

class RemoteAkunLogicImpl :
    RemoteAkunLogic {

    private var dataAkun = MutableLiveData<Resource<Akun>>()

    override fun createAkun(akun: Akun): String {
        val dbAkun = Firebase.database.getReference("akun")
        val akunKey = dbAkun.push().key!!
        akun.akun_id = akunKey
        dbAkun.child(akunKey).setValue(akun)

//        Add Initial Kategori (Non Kategori)
        val dbKategori = Firebase.database.getReference("kategori").child(akunKey)

        val newKategori = Kategori()
        newKategori.nama = "Non Kategori"
        newKategori.foto = Constants.defaultImageObject
        newKategori.uuid = "nonKategori"

        dbKategori.child("nonKategori").setValue(newKategori)

        return akunKey
    }

    override fun getAkun(): MutableLiveData<Resource<Akun>> {
        return dataAkun
    }

    override fun fetchAkun(username: String) {
        val dbAkun = Firebase.database.getReference("akun")
        dbAkun.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                fetchLiveBarang()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                fetchLiveBarang()
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                setLiveBarang(listBarang)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
//                setLiveBarang(listBarang)
            }
        })

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