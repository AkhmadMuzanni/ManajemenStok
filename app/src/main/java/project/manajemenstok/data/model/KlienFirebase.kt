package project.manajemenstok.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class KlienFirebase (
    @SerializedName("uuid")
    var uuid: String="",
    @SerializedName("nama")
    var nama: String="",
    @SerializedName("noTelp")
    var noTelp: String=""
): Serializable
