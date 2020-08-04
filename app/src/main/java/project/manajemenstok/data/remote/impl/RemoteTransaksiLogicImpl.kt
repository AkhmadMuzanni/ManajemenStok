package project.manajemenstok.data.remote.impl

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.manajemenstok.data.model.*
import project.manajemenstok.data.remote.logic.RemoteTransaksiLogic
import project.manajemenstok.utils.Resource

class RemoteTransaksiLogicImpl :
    RemoteTransaksiLogic {
    private var liveDataTransaksi = MutableLiveData<Resource<ArrayList<TransaksiData>>>()
    private var liveDataDetailTransaksi = MutableLiveData<Resource<ArrayList<DetailTransaksiData>>>()

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

    override fun getTransaksi(): MutableLiveData<Resource<ArrayList<TransaksiData>>> {
        return liveDataTransaksi
    }

    override fun fetchTransaksi() {
        getDbReference("transaksi").orderByPriority().addListenerForSingleValueEvent(object :
            ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                var listAllTransaksi = ArrayList<TransaksiData>()
                snapshot.children.forEach{
                    it.children.forEach {
                        var transaksi = it.getValue<TransaksiFirebase>(TransaksiFirebase::class.java)!!
                        getDbReference("klien").child(transaksi.idKlien).addListenerForSingleValueEvent(object :
                            ValueEventListener{
                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                val klien = snapshot.getValue<KlienFirebase>(KlienFirebase::class.java)!!
                                val tempTransaksiData = TransaksiData()
                                tempTransaksiData.uuid = transaksi.uuid
                                tempTransaksiData.namaKlien = klien.nama
                                tempTransaksiData.tglTransaksi = transaksi.tglTransaksi
                                tempTransaksiData.ongkir = transaksi.ongkir
                                tempTransaksiData.totalTransaksi = transaksi.totalTransaksi
                                tempTransaksiData.metode = transaksi.metode
                                tempTransaksiData.jenisTransaksi = transaksi.jenisTransaksi
                                listAllTransaksi.add(tempTransaksiData)
                                setTransaksi(listAllTransaksi)

                            }
                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    override fun setTransaksi(listTransaksi: ArrayList<TransaksiData>) {
        liveDataTransaksi.postValue(Resource.success(listTransaksi))
    }

    override fun getDetailTransaksi(): MutableLiveData<Resource<ArrayList<DetailTransaksiData>>> {
        return liveDataDetailTransaksi
    }

    override fun fetchDetailTransaksi(param: String) {
        getDbReference("detailTransaksi").addListenerForSingleValueEvent(object:
        ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var listDetailTransaksi = ArrayList<DetailTransaksiData>()
                snapshot.children.forEach {
                    it.children.forEach{
                        if (param.equals(it.key)){
                            it.children.forEach{
                                var detailTransaksi = it.getValue<DetailTransaksiFirebase>(DetailTransaksiFirebase::class.java)!!
                                getDbReference("barang").child(detailTransaksi.idBarang).addListenerForSingleValueEvent(object :
                                    ValueEventListener{
                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }

                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val barang = snapshot.getValue<Barang>(Barang::class.java)!!
                                        val tempDetailTransaksi = DetailTransaksiData()
                                        tempDetailTransaksi.uuid = detailTransaksi.uuid
                                        tempDetailTransaksi.idTransaksi = detailTransaksi.idTransaksi
                                        tempDetailTransaksi.namaBarang = barang.namaBarang
                                        tempDetailTransaksi.harga = detailTransaksi.harga
                                        tempDetailTransaksi.jumlah = detailTransaksi.jumlah
                                        tempDetailTransaksi.total = detailTransaksi.total
                                        listDetailTransaksi.add(tempDetailTransaksi)
                                        setDetailTransaksi(listDetailTransaksi)
                                    }

                                })

                            }
                        }
                    }
                }
            }

        })
    }

    override fun setDetailTransaksi(listDetailTransaksi: ArrayList<DetailTransaksiData>) {
        liveDataDetailTransaksi.postValue(Resource.success(listDetailTransaksi))
    }


}