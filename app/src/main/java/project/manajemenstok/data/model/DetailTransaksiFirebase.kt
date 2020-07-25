package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DetailTransaksiFirebase (
    @SerializedName("uuid")
    var uuid: String="",
    @SerializedName("idTransaksi")
    var idTransaksi: String="",
    @SerializedName("idBarang")
    var idBarang: String="",
    @SerializedName("harga")
    var harga: Int=0,
    @SerializedName("jumlah")
    var jumlah: Int=0,
    @SerializedName("total")
    var total: Int=0
): Serializable