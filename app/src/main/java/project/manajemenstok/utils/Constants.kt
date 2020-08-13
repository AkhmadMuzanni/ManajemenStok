package project.manajemenstok.utils

import project.manajemenstok.data.model.Akun

class Constants {
    companion object {
        val defaultImageObject = "https://firebasestorage.googleapis.com/v0/b/manajemenstok-4d541.appspot.com/o/bevyStock%2Fdefault.png?alt=media&token=a1b1e484-cbc0-41c8-822a-29641ca6ef31"
        val TIME_SPLASH_SCREEN: Long = 4000
        var USER_ID = "akun2"
        var CONSAKUN = Akun()
        val BUCKER_FOLDER = "bevyStock/$USER_ID/"
    }

    class JenisTransaksiValue{
        companion object {
            val PEMBELIAN = 0
            val PENJUALAN = 1
        }
    }

    class MetodePembayaran{
        companion object {
            val CASH = 0
            val DEBET = 1
            val KREDIT = 2
        }
    }

    class RequestCodeIntent{
        companion object {
            val INPUT_BARANG = 1
            val KONFIRMASI_TRANSAKSI = 2
            val DETAIL_BARANG = 3
            val GET_IMAGE = 4
            val DETAIL_KATEGORI = 5
        }
    }

    class DeleteStatus{
        companion object {
            val ACTIVE = 0
            val DELETED = 1
        }
    }

    class IntentMode{
        companion object {
            val ADD = 0
            val EDIT = 1
        }
    }
}