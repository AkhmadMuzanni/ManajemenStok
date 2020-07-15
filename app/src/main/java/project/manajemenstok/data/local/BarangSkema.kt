package project.manajemenstok.data.local

data class BarangSkema(val id:Long?, val namaBarang: String, val hargaBeli: Int, val foto: String, val jumlah: Int, val total: Int) {
    companion object {
        const val TABLE_BARANG : String = "TABLE_BARANG"
        const val ID: String = "ID_"
        const val NAMA_BARANG: String = "NAMA_BARANG"
        const val HARGA_BELI: String = "HARGA_BELI"
        const val FOTO: String = "FOTO"
        const val JUMLAH: String = "JUMLAH"
        const val TOTAL: String = "TOTAL"
    }


}