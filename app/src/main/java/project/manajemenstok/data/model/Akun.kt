package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Akun (
    @SerializedName("uuid")
    var akun_id: String="",
    @SerializedName("nama")
    var nama: String="",
    @SerializedName("nama_toko")
    var nama_toko: String="",
    @SerializedName("paket")
    var paket: String="",
    @SerializedName("dtm_crt")
    var dateCreated: String=""
): Serializable
