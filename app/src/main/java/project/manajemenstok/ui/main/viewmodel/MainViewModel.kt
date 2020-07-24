package project.manajemenstok.ui.main.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import project.manajemenstok.data.local.BarangDbHelper
import project.manajemenstok.data.model.Barang
import project.manajemenstok.data.remote.RemoteBrangLogicImpl
import project.manajemenstok.data.repository.BarangRepository
import project.manajemenstok.utils.Resource

class MainViewModel (val context : Context, private val is_remote : Boolean) : ViewModel() {

    private val barangRepository = BarangRepository(
        RemoteBrangLogicImpl(),
        BarangDbHelper(context)
    )
    private val barangs = MutableLiveData<Resource<List<Barang>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        checkBarang()
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

    fun getDbReference(query: String): DatabaseReference{
        return barangRepository.getDbReference(query)
    }
}