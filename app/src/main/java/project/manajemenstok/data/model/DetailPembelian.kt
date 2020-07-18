package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName

data class DetailPembelian (
    @SerializedName("idPembelian")
    var idPembelian: Int=0,
    @SerializedName("idBarang")
    var idBarang: Int=0,
    @SerializedName("harga")
    val harga: Int=0,
    @SerializedName("ongkir")
    var ongkir: Int=0,
    @SerializedName("jumlah")
    var jumlah: Int=0,
    @SerializedName("metode")
    val metode: Int=0

)