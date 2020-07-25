package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TransaksiFirebase (
    @SerializedName("uuid")
    var uuid: String="",
    @SerializedName("idKlien")
    var idKlien: String="",
    @SerializedName("tglTransaksi")
    var tglTransaksi: String = "",
    @SerializedName("ongkir")
    var ongkir: Int=0,
    @SerializedName("totalTransaksi")
    var totalTransaksi: Int=0,
    @SerializedName("metode")
    var metode: Int=0,
    @SerializedName("jenisTransaksi")
    var jenisTransaksi: Int=0
): Serializable
