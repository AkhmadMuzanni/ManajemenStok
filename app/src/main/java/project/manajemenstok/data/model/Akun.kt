package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Akun (
    @SerializedName("uuid")
    var akun_id: String="",
    @SerializedName("email")
    var email: String="",
    @SerializedName("password")
    var password: String="",
    @SerializedName("nama")
    var nama: String="",
    @SerializedName("nama_toko")
    var nama_toko: String="",
    @SerializedName("paket")
    var paket: String="",
    @SerializedName("dtm_crt")
    var dtm_crt: String="",
    @SerializedName("dtm_upd")
    var dtm_upd: String=""
): Serializable
