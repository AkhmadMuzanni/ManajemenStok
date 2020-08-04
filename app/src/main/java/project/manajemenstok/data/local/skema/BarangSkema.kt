package project.manajemenstok.data.local.skema

data class BarangSkema(val id:Long?, val namaBarang: String, val hargaBeli: Int, val foto: String, val jumlah: Int, val total: Int, val uuid: String, val maxQuantity: Int, val isDeleted: Int, val kategori: String) {
    companion object {
        const val TABLE_BARANG : String = "TABLE_BARANG"
        const val ID: String = "ID_"
        const val NAMA_BARANG: String = "NAMA_BARANG"
        const val HARGA_BELI: String = "HARGA_BELI"
        const val FOTO: String = "FOTO"
        const val JUMLAH: String = "JUMLAH"
        const val TOTAL: String = "TOTAL"
        const val UUID: String = "UUID"
        const val MAX_QUANTITY: String = "MAX_QUANTITY"
        const val IS_DELETED: String = "IS_DELETED"
        const val KATEGORI: String = "KATEGORI"
    }


}