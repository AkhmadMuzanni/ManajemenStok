package project.manajemenstok.data.remote.impl

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.data.remote.logic.RemoteKategoriLogic
import project.manajemenstok.utils.Constants
import project.manajemenstok.utils.Resource

class RemoteKategoriLogicImpl :
    RemoteKategoriLogic {

    private var liveDataKategori = MutableLiveData<Resource<ArrayList<Kategori>>>()
    private var imageUrl = MutableLiveData<String>()
    private var listBarangKategori = MutableLiveData<Resource<ArrayList<Barang>>>()

    override fun getDbReference(query: String): DatabaseReference {
        val database = Firebase.database
        return database.getReference(query)
    }

    override fun getKategori(): MutableLiveData<Resource<ArrayList<Kategori>>> {
        return liveDataKategori
    }

    override fun fetchKategori() {
        getDbReference("kategori").addChildEventListener(object : ChildEventListener {
            var listKategori = ArrayList<Kategori>()
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

        getDbReference("kategori").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var result = ArrayList<Kategori>()
                snapshot.children.forEach{
                    val tempKategori = it.getValue<Kategori>(Kategori::class.java)!!
                    if(tempKategori.isDeleted == Constants.DeleteStatus.ACTIVE){
                        result.add(tempKategori)
                    }
                }
                setKategori(result)
            }

        })
    }

    override fun setKategori(listKategori: ArrayList<Kategori>) {
        liveDataKategori.postValue(Resource.success(listKategori))
    }

    override fun updateKategori(kategori: Kategori) {
        val dbKategori = getDbReference("kategori")
        val kategoriUpdates: MutableMap<String, Any> = HashMap()
        kategoriUpdates[kategori.uuid] = kategori

        dbKategori.updateChildren(kategoriUpdates)
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
        val storageRef = Firebase.storage.reference.child(path)

        val uploadTask = storageRef.putFile(imageUri)

        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            fetchImageUrl(storageRef.path)
        }
    }

    override fun getStorageReference(query: String): StorageReference {
        return Firebase.storage.reference.child(query)
    }

    override fun createKategori(kategori: Kategori): String {
        val dbKategori = getDbReference("kategori")
        val key = dbKategori.push().key!!
        kategori.uuid = key
        dbKategori.child(key).setValue(kategori)
        return key
    }

    override fun syncKategori() {
        getDbReference("barang").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var result = ArrayList<Barang>()
                snapshot.children.forEach{
                    val tempBarang = it.getValue<Barang>(Barang::class.java)!!
                    result.add(tempBarang)
                }

                countKategoriOnBarang(result)
            }
        })
    }

    override fun countKategoriOnBarang(listBarang: ArrayList<Barang>) {
        getDbReference("kategori").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var listKategori = ArrayList<Kategori>()
                snapshot.children.forEach{
                    val tempKategori = it.getValue<Kategori>(Kategori::class.java)!!
                    tempKategori.jumlah = 0
                    if(tempKategori.isDeleted == Constants.DeleteStatus.ACTIVE){
                        listKategori.add(tempKategori)
                    }
                }

                var idxNonKategori = 0
                var countDeleted = 0
                for(barang in listBarang){
                    if(barang.isDeleted == Constants.DeleteStatus.ACTIVE){
                        var isDeleted = true
                        for((idx, kategori) in listKategori.withIndex()){
                            if(barang.kategori == kategori.uuid){
                                kategori.jumlah += 1
                                isDeleted = false
                            }
                            if(kategori.uuid == "nonKategori"){
                                idxNonKategori = idx
                            }
                        }
                        if(isDeleted){
                            barang.kategori = "nonKategori"
                            updateBarang(barang)
                            countDeleted += 1
                        }
                    }
                }

                listKategori[idxNonKategori].jumlah += countDeleted

                for(kategori in listKategori){
                    updateKategori(kategori)
                }
                setKategori(listKategori)
            }
        })
    }

    override fun updateBarang(barang: Barang) {
        val dbBarang = getDbReference("barang")
        val barangUpdates: MutableMap<String, Any> = HashMap()
        barangUpdates[barang.uuid] = barang

        dbBarang.updateChildren(barangUpdates)
    }

    override fun getBarangKategori(): MutableLiveData<Resource<ArrayList<Barang>>> {
        return listBarangKategori
    }

    override fun fetchBarangKategori(uuidKategori: String) {
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
                    if(tempBarang.isDeleted == Constants.DeleteStatus.ACTIVE && tempBarang.kategori == uuidKategori){
                        listBarang.add(tempBarang)
                    }
                }
                setBarangKategori(listBarang)
            }

            override fun onCancelled(databaseError: DatabaseError) {
//                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
    }

    override fun setBarangKategori(listBarang: ArrayList<Barang>) {
        listBarangKategori.postValue(Resource.success(listBarang))
    }


}