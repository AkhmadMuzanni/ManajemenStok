package project.manajemenstok.data.remote.impl

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.remote.logic.RemoteBrangLogic

class RemoteBarangLogicImpl : RemoteBrangLogic {
    val database = Firebase.database
    private var liveBarang = MutableLiveData<ArrayList<Barang>>()

    override fun getBarangs(): Single<List<Barang>> {
//        Get Live Data
//        return Rx2AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
//            .build()
//            .getObjectListSingle(Barang::class.java)
        return Rx2AndroidNetworking.get("https://tomatoleafdisease.web.app/testData4.json")
            .build()
            .getObjectListSingle(Barang::class.java)
    }

    override fun getDbReference(query: String): DatabaseReference {
        return database.getReference(query)
    }

    override fun setLiveBarang(listBarang: ArrayList<Barang>) {
        liveBarang.postValue(listBarang)
    }

    override fun getLiveBarang(): MutableLiveData<ArrayList<Barang>> {
        return liveBarang
    }

    override fun fetchLiveBarang() {
        getDbReference("barang").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var listBarang = ArrayList<Barang>()
                dataSnapshot.children.forEach {
                    listBarang.add(it.getValue<Barang>(Barang::class.java)!!)
                }
                setLiveBarang(listBarang)
            }

            override fun onCancelled(databaseError: DatabaseError) {
//                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
    }

}