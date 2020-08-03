package project.manajemenstok.data.remote.logic

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import project.manajemenstok.data.model.DetailTransaksiData
import project.manajemenstok.data.model.DetailTransaksiFirebase
import project.manajemenstok.data.model.TransaksiData
import project.manajemenstok.data.model.TransaksiFirebase
import project.manajemenstok.utils.Resource

interface RemoteTransaksiLogic {
    fun getDbReference(query: String): DatabaseReference
    fun createTransaksi(transaksi: TransaksiFirebase): String
    fun createDetailTransaksi(detailTransaksi: DetailTransaksiFirebase, idDetailTransaksi: String): String
    fun getTransaksi(): MutableLiveData<Resource<ArrayList<TransaksiData>>>
    fun fetchTransaksi()
    fun setTransaksi(listTransaksi: ArrayList<TransaksiData>)
    fun getDetailTransaksi(): MutableLiveData<Resource<ArrayList<DetailTransaksiData>>>
    fun fetchDetailTransaksi(param: String)
    fun setDetailTransaksi(listDetailTransaksi: ArrayList<DetailTransaksiData>)
}