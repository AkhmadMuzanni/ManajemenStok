package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName

data class Pembelian (
    @SerializedName("id")
    var id: Int=0,
    @SerializedName("idPenjual")
    var idPenjual: Int=0,
    @SerializedName("tglPembelian")
    val tglPembelian: String = "",
    @SerializedName("ongkir")
    var ongkir: Int=0,
    @SerializedName("totalPembelian")
    var totalPembelian: Int=0,
    @SerializedName("metode")
    val metode: Int=0

)
