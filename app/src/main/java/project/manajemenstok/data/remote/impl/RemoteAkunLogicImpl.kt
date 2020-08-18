package project.manajemenstok.data.remote.impl

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.*
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
import project.manajemenstok.utils.Helper
import project.manajemenstok.utils.Resource
import java.lang.Exception

class RemoteAkunLogicImpl :
    RemoteAkunLogic {

    private var dataAkun = MutableLiveData<Resource<Akun>>()
    private var imageUrl = MutableLiveData<String>()

    override fun createAkun(akun: Akun, uuid: String): String {
        val dbAkun = Firebase.database.getReference("akun")
//        val akunKey = dbAkun.push().key!!
        akun.akun_id = uuid
        dbAkun.child(uuid).setValue(akun)

//        Add Initial Kategori (Non Kategori)
        val dbKategori = Firebase.database.getReference("kategori").child(uuid)

        val newKategori = Kategori()
        newKategori.nama = "Non Kategori"
        newKategori.foto = Constants.defaultImageObject
        newKategori.uuid = "nonKategori"

        dbKategori.child("nonKategori").setValue(newKategori)

        return uuid
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

    private var user = FirebaseAuth.getInstance().currentUser
    override fun updateAkun(akun: Akun, uuid: String, email: String): Resource<String> {
        val dbAkun = Firebase.database.getReference("akun")
        val akunUpdates: MutableMap<String, Any> = HashMap()
        akunUpdates[uuid] = akun

        try {
            dbAkun.updateChildren(akunUpdates).addOnCompleteListener {
                Constants.CONSAKUN = akun
                changeEmail(email, Constants.CONSAKUN.password)
            }
            return Resource.success(uuid)
        }catch (e: Exception){
            return Resource.error(e.toString(),uuid)
        }
    }



    override fun fetchAkunById(uuid: String) {
        Firebase.database.getReference("akun").child(uuid).addListenerForSingleValueEvent(object :
        ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var akun  = snapshot.getValue<Akun>(Akun::class.java)!!
                setAkun(akun)
            }

        })
    }

    override fun updatePassword(oldPass: String, newPass :String): Resource<String> {
        var isGoal = false
        user?.let {
            val cred = EmailAuthProvider.getCredential(it.email!!,oldPass)
            it.reauthenticate(cred).addOnCompleteListener{
                if (it.isSuccessful){
                    user!!.updatePassword(newPass)
                    updateEmailAndPass(Constants.CONSAKUN.email, newPass)
                    isGoal = true
                }else if (it.exception is FirebaseAuthInvalidCredentialsException){
                    TODO("Not yet implemented")
                }else{
                    TODO("Not yet implemented")
                }
            }
        }

        return Resource.success(user?.uid)
    }

    private fun changeEmail(email: String, password: String){
        user?.let {
            val cred = EmailAuthProvider.getCredential(it.email!!,password)
            it.reauthenticate(cred).addOnCompleteListener{
                if (it.isSuccessful){
                    user!!.updateEmail(email)
                    updateEmailAndPass(email, password)
                }else if (it.exception is FirebaseAuthInvalidCredentialsException){
                    TODO("Not yet implemented")
                }else{
                    TODO("Not yet implemented")
                }
            }
        }

    }

    private fun updateEmailAndPass(email: String, pass: String){
        val dbAkun = Firebase.database.getReference("akun")
        val akunUpdates: MutableMap<String, Any> = HashMap()
        var akun = Constants.CONSAKUN
        akun.email = email
        akun.password = pass
        akunUpdates[user?.uid.toString()] = akun
        dbAkun.updateChildren(akunUpdates)

    }


    override fun uploadImage(imageUri: Uri, path: String) {
        val storageRef = Helper.getStorageReference(path)

        val uploadTask = storageRef.putFile(imageUri)

        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
//            var akun = Constants.CONSAKUN
//            akun.foto = path
//            updateAkun(akun,Constants.CONSAKUN.akun_id,Constants.CONSAKUN.email)
            fetchImageUrl(path)
        }
    }

    override fun fetchImageUrl(path: String) {
        val imageReference = Helper.getStorageReference(path)
        imageReference.downloadUrl.addOnSuccessListener {
            setImageUrl(it.toString())
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    override fun getImageUrl(): MutableLiveData<String> {
        return imageUrl
    }

    override fun setImageUrl(url: String) {
        imageUrl.postValue(url)
    }

}