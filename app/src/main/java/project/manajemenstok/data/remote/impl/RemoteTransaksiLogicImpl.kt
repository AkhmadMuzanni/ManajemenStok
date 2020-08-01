package project.manajemenstok.data.remote.impl

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.manajemenstok.data.model.DetailTransaksiFirebase
import project.manajemenstok.data.model.TransaksiFirebase
import project.manajemenstok.data.remote.logic.RemoteTransaksiLogic
import project.manajemenstok.utils.Resource

class RemoteTransaksiLogicImpl :
    RemoteTransaksiLogic {
    private var liveDataTransaksi = MutableLiveData<Resource<ArrayList<TransaksiFirebase>>>()
    private var liveDataDetailTransaksi = MutableLiveData<Resource<ArrayList<DetailTransaksiFirebase>>>()

    override fun getDbReference(query: String): DatabaseReference {
        val database = Firebase.database
        return database.getReference(query)
    }

    override fun createTransaksi(transaksi: TransaksiFirebase): String {
        val dbTransaksi = getDbReference("transaksi")
        val key = dbTransaksi.push().key!!
        transaksi.uuid = key
        dbTransaksi.child(key).child(transaksi.idKlien).setValue(transaksi)
        return key
    }

    override fun createDetailTransaksi(detailTransaksi: DetailTransaksiFirebase, idDetailTransaksi: String): String {
        val dbTransaksi = getDbReference("detailTransaksi")
        var key = ""
        if(idDetailTransaksi == ""){
            key = dbTransaksi.push().key!!
        } else {
            key = idDetailTransaksi
        }
        detailTransaksi.uuid = key
        dbTransaksi.child(key).child(detailTransaksi.idTransaksi).child(detailTransaksi.idBarang).setValue(detailTransaksi)
        return key
    }

    override fun getTransaksi(): MutableLiveData<Resource<ArrayList<TransaksiFirebase>>> {
        return liveDataTransaksi
    }

    override fun fetchTransaksi() {
        getDbReference("transaksi").addListenerForSingleValueEvent(object :
            ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                var listAllTransaksi = ArrayList<TransaksiFirebase>()
                snapshot.children.forEach{
                    it.children.forEach {
                        listAllTransaksi.add(it.getValue<TransaksiFirebase>(TransaksiFirebase::class.java)!!)
                    }
                }

                setTransaksi(listAllTransaksi)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun setTransaksi(listTransaksi: ArrayList<TransaksiFirebase>) {
        liveDataTransaksi.postValue(Resource.success(listTransaksi))
    }

    override fun getDetailTransaksi(): MutableLiveData<Resource<ArrayList<DetailTransaksiFirebase>>> {
        return liveDataDetailTransaksi
    }

    override fun fetchDetailTransaksi(param: String) {
        getDbReference("detailTransaksi").addListenerForSingleValueEvent(object:
        ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var listDetailTransaksi = ArrayList<DetailTransaksiFirebase>()
                snapshot.children.forEach {
                    it.children.forEach{
                        if (param.equals(it.key)){
                            it.children.forEach{
                                listDetailTransaksi.add(it.getValue<DetailTransaksiFirebase>(DetailTransaksiFirebase::class.java)!!)
                            }
                        }
                    }
                }
                setDetailTransaksi(listDetailTransaksi)
            }

        })
    }

    override fun setDetailTransaksi(listDetailTransaksi: ArrayList<DetailTransaksiFirebase>) {
        liveDataDetailTransaksi.postValue(Resource.success(listDetailTransaksi))
    }


}