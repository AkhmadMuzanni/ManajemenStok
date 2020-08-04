package project.manajemenstok.data.remote.impl

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.data.remote.logic.RemoteKategoriLogic
import project.manajemenstok.utils.Resource

class RemoteKategoriLogicImpl :
    RemoteKategoriLogic {

    private var liveDataKategori = MutableLiveData<Resource<ArrayList<Kategori>>>()

    override fun getDbReference(query: String): DatabaseReference {
        val database = Firebase.database
        return database.getReference(query)
    }

    override fun getKategori(): MutableLiveData<Resource<ArrayList<Kategori>>> {
        return liveDataKategori
    }

    override fun fetchKategori() {
        getDbReference("kategori").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var result = ArrayList<Kategori>()
                snapshot.children.forEach{
                    val tempKategori = it.getValue<Kategori>(Kategori::class.java)!!
                    result.add(tempKategori)
                }
                setKategori(result)
            }

        })
    }

    override fun setKategori(listKategori: ArrayList<Kategori>) {
        liveDataKategori.postValue(Resource.success(listKategori))
    }

}