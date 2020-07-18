package project.manajemenstok.ui.main.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import project.manajemenstok.data.local.BarangDbHelper
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.model.Penjual
import project.manajemenstok.data.remote.RemoteBrangLogicImpl
import project.manajemenstok.data.remote.RemotePembelianLogicImpl
import project.manajemenstok.data.remote.RemotePenjualLogicImpl
import project.manajemenstok.data.repository.BarangRepository
import project.manajemenstok.data.repository.PembelianRepository
import project.manajemenstok.data.repository.PenjualRepository
import project.manajemenstok.utils.Resource

class PembelianViewModel (val context : Context, private val is_remote : Boolean) : ViewModel() {

    private val barangRepository = BarangRepository(
        RemoteBrangLogicImpl(),
        BarangDbHelper(context)
    )
    private val barangs = MutableLiveData<Resource<List<Barang>>>()
    private val penjuals = MutableLiveData<Resource<List<Penjual>>>()

    private val compositeDisposable = CompositeDisposable()

    private val penjualRepository = PenjualRepository(
        RemotePenjualLogicImpl(),
        BarangDbHelper(context)
    )

    private val pembelianRepository = PembelianRepository(
        RemotePembelianLogicImpl(),
        BarangDbHelper(context)
    )

    init {
        checkBarang()
        fetchPenjual()
    }

    @SuppressLint("CheckResult")
    private fun getDataRemote(){
        barangRepository.getBarangs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                barangRepository.setBarangFromRemote(it)
                fetchBarang()
            }, { throwable ->
                barangs.postValue(Resource.error("Something Went Wrong", null))
            })
    }

    @SuppressLint("CheckResult")
    private fun fetchBarang(){
        barangs.postValue(Resource.loading(null))
        if(is_remote){
            compositeDisposable.add(
                barangRepository.getBarangsLocal()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ barangList ->
                        barangs.postValue(Resource.success(barangList))
                    }, { throwable ->
                        barangs.postValue(Resource.error("Something Went Wrong", null))
                    })
            )
        }
        else {
            barangs.postValue(Resource.success(null))
        }

    }

    private fun checkBarang(){
        barangs.postValue(Resource.loading(null))
        compositeDisposable.add(
            barangRepository.getBarangsLocal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ barangList ->
                    if(barangList.isEmpty()){
                        getDataRemote()
                    } else {
                        fetchBarang()
                    }
                }, { throwable ->
                    barangs.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getBarangs(): LiveData<Resource<List<Barang>>> {
        return barangs
    }

    fun addTempBarang(dataBarang: Barang){
        barangRepository.addTempBarang(dataBarang)
    }

    fun getTempBarang(): ArrayList<Barang>{
        return barangRepository.getTempBarang()
    }

    private fun fetchPenjual(){
        penjuals.postValue(Resource.loading(null))
        compositeDisposable.add(
            penjualRepository.getPenjualLocal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ penjualList ->
                    penjuals.postValue(Resource.success(penjualList))
                }, { throwable ->
                    penjuals.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    fun setTempBarang(dataBarang: ArrayList<Barang>){
        barangRepository.setTempBarang(dataBarang)
    }

    fun getTotalTransaksi(): Int{
        val dataBarang = getTempBarang()
        var total = 0
        for (barang in dataBarang) {
            total += barang.total
        }
        total += getTempOngkir()
        return total
    }

    fun setTempOngkir(ongkir: Int){
        pembelianRepository.setTempOngkir(ongkir)
    }

    fun getTempOngkir(): Int{
        return pembelianRepository.getTempOngkir()
    }

    fun getPenjual(): LiveData<Resource<List<Penjual>>> {
        return penjuals
    }

    fun setTempPenjual(dataPenjual: String){
        penjualRepository.setTempPenjual(dataPenjual)
    }

    fun getTempPenjual(): String{
        return penjualRepository.getTempPenjual()
    }

    fun simpanPembelian(){
        Toast.makeText(context, "Simpan Pembelian", Toast.LENGTH_LONG).show()
//        savePenjual()
//        savePembelian()
//        saveDetailPembelian()
//        updateStok()
    }
}