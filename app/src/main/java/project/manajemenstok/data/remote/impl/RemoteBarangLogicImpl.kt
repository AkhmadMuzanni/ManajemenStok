package project.manajemenstok.data.remote.impl

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.remote.logic.RemoteBarangLogic
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Helper.Companion.getDbReference
import project.manajemenstok.utils.Helper.Companion.getStorageReference

class RemoteBarangLogicImpl : RemoteBarangLogic {
    private var liveBarang = MutableLiveData<ArrayList<Barang>>()
    private var unusedBarang = MutableLiveData<ArrayList<Barang>>()
    private var tempBarang = MutableLiveData<Barang>()
    private var barangTransaksi = MutableLiveData<ArrayList<Barang>>()
    private var imageUrl = MutableLiveData<String>()

    override fun getBarangs(): Single<List<Barang>> {
//        Get Live Data
//        return Rx2AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
//            .build()
//            .getObjectListSingle(Barang::class.java)
        return Rx2AndroidNetworking.get("https://tomatoleafdisease.web.app/testData4.json")
            .build()
            .getObjectListSingle(Barang::class.java)
    }


    override fun setLiveBarang(listBarang: ArrayList<Barang>) {
        liveBarang.postValue(listBarang)
    }

    override fun getUnusedBarang(): MutableLiveData<ArrayList<Barang>>{
        return unusedBarang
    }

    override fun fetchUnusedBarang(barangUsed: ArrayList<Barang>) {
        getDbReference("barang").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var result = ArrayList<Barang>()
                dataSnapshot.children.forEach {
                    val barang = it.getValue<Barang>(Barang::class.java)!!
                    if(barang.isDeleted == Constants.DeleteStatus.ACTIVE){
                        var isUsed = false
                        for(used in barangUsed){
                            if(barang.uuid == used.uuid){
                                isUsed = true
                                break
                            }
                        }
                        if(!isUsed){
                            result.add(barang)
                        }
                    }
                }
                setUnusedBarang(result)
            }

            override fun onCancelled(databaseError: DatabaseError) {
//                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
    }

    override fun fetchUnusedBarang(barangUsed: ArrayList<Barang>, query: String) {
        getDbReference("barang").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var result = ArrayList<Barang>()
                dataSnapshot.children.forEach {
                    val barang = it.getValue<Barang>(Barang::class.java)!!
                    if(barang.isDeleted == Constants.DeleteStatus.ACTIVE && barang.namaBarang.toLowerCase().contains(query)){
                        var isUsed = false
                        for(used in barangUsed){
                            if(barang.uuid == used.uuid){
                                isUsed = true
                                break
                            }
                        }
                        if(!isUsed){
                            result.add(barang)
                        }
                    }
                }
                setUnusedBarang(result)
            }

            override fun onCancelled(databaseError: DatabaseError) {
//                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
    }

    override fun setUnusedBarang(listBarang: ArrayList<Barang>) {
        unusedBarang.postValue(listBarang)
    }

    override fun createBarang(barang: Barang): String {
        val dbKlien = getDbReference("barang")
        val key = dbKlien.push().key!!
        barang.uuid = key
        dbKlien.child(key).setValue(barang)
        return key
    }

    override fun getBarangById(): MutableLiveData<Barang> {
        return tempBarang
    }

    override fun fetchBarangById(id: String) {
        getDbReference("barang/$id").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                setBarangById(dataSnapshot.getValue<Barang>(Barang::class.java)!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {
//                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
    }

    override fun setBarangById(barang: Barang) {
        tempBarang.postValue(barang)
    }

    override fun updateBarang(barang: Barang) {
        val dbBarang = getDbReference("barang")
        val barangUpdates: MutableMap<String, Any> = HashMap()
        barangUpdates[barang.uuid] = barang

        dbBarang.updateChildren(barangUpdates)
    }

    override fun getBarangTransaksi(): MutableLiveData<ArrayList<Barang>> {
        return barangTransaksi
    }

    override fun fetchBarangTransaksi(listBarang: ArrayList<Barang>) {
        getDbReference("barang").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var result = ArrayList<Barang>()
                for(barang in listBarang){
                    var isUsed = false
//                    var existedBarang = Barang()
                    var resultBarang = Barang()
                    dataSnapshot.children.forEach {
                        var existedBarang = it.getValue<Barang>(Barang::class.java)!!
                        if(barang.uuid == existedBarang.uuid && existedBarang.isDeleted == Constants.DeleteStatus.ACTIVE){
                            isUsed = true
                            resultBarang = existedBarang
                        }
                    }
                    if(isUsed){
                        result.add(resultBarang)
                    } else {
                        result.add(barang)
                    }
                }

                setBarangTransaksi(result)
            }

            override fun onCancelled(databaseError: DatabaseError) {
//                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
    }

    override fun setBarangTransaksi(listBarang: ArrayList<Barang>) {
        barangTransaksi.postValue(listBarang)
    }

    override fun getImageUrl(): MutableLiveData<String> {
        return imageUrl
    }

    override fun fetchImageUrl(path: String) {
        val imageReference = getStorageReference(path)
        imageReference.downloadUrl.addOnSuccessListener {
            setImageUrl(it.toString())
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    override fun setImageUrl(url: String) {
        imageUrl.postValue(url)
    }

    override fun uploadImage(imageUri: Uri, path: String) {
        val storageRef = getStorageReference(path)

        val uploadTask = storageRef.putFile(imageUri)

        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            fetchImageUrl(path)
        }
    }

    override fun getLiveBarang(): MutableLiveData<ArrayList<Barang>> {
        return liveBarang
    }

    override fun fetchLiveBarang() {
        getDbReference("barang").addChildEventListener(object : ChildEventListener{
            var listBarang = ArrayList<Barang>()
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

        getDbReference("barang").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var listBarang = ArrayList<Barang>()
                dataSnapshot.children.forEach {
                    val tempBarang = it.getValue<Barang>(Barang::class.java)!!
                    if(tempBarang.isDeleted == Constants.DeleteStatus.ACTIVE){
                        listBarang.add(tempBarang)
                    }
                }
                setLiveBarang(listBarang)
            }

            override fun onCancelled(databaseError: DatabaseError) {
//                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
    }

    override fun fetchLiveBarang(query: String) {
        getDbReference("barang").addChildEventListener(object : ChildEventListener{
            var listBarang = ArrayList<Barang>()
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

        getDbReference("barang").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var listBarang = ArrayList<Barang>()
                dataSnapshot.children.forEach {
                    val tempBarang = it.getValue<Barang>(Barang::class.java)!!
                    if(tempBarang.isDeleted == Constants.DeleteStatus.ACTIVE && tempBarang.namaBarang.toLowerCase().contains(query)){
                        listBarang.add(tempBarang)
                    }
                }
                setLiveBarang(listBarang)
            }

            override fun onCancelled(databaseError: DatabaseError) {
//                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
    }

}