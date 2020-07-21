package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pembelian (
    @SerializedName("id")
    var id: Int=0,
    @SerializedName("idPenjual")
    var idPenjual: Int=0,
    @SerializedName("tglPembelian")
    var tglPembelian: String = "",
    @SerializedName("ongkir")
    var ongkir: Int=0,
    @SerializedName("totalPembelian")
    var totalPembelian: Int=0,
    @SerializedName("metode")
    var metode: Int=0

): Serializable
