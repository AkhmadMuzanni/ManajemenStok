package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName

data class Pembelian (
    @SerializedName("id")
    var id: Int=0,
    @SerializedName("idPenjual")
    var idPenjual: Int=0,
    @SerializedName("tglPembelian")
    val tglPembelian: String = "",
    @SerializedName("totalPembelian")
    var totalPembelian: Int=0

)
