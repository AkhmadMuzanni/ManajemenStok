package project.manajemenstok.data.remote.logic

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import project.manajemenstok.data.model.*
import project.manajemenstok.utils.Resource

interface RemoteKategoriLogic {
    fun getDbReference(query: String): DatabaseReference
    fun getKategori(): MutableLiveData<Resource<ArrayList<Kategori>>>
    fun fetchKategori()
    fun setKategori(listKategori: ArrayList<Kategori>)
}