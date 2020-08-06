package project.manajemenstok.data.remote.impl

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import project.manajemenstok.data.model.Kategori
import project.manajemenstok.data.remote.logic.RemoteKategoriLogic
import project.manajemenstok.utils.Resource

class RemoteKategoriLogicImpl :
    RemoteKategoriLogic {

    private var liveDataKategori = MutableLiveData<Resource<ArrayList<Kategori>>>()
    private var imageUrl = MutableLiveData<String>()

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

}