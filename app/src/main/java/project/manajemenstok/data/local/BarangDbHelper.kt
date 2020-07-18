package project.manajemenstok.data.local

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import project.manajemenstok.data.model.Barang

class BarangDbHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "ManajemenStok.db", null, 1),
    BarangLogic, PenjualLogic, PembelianLogic {

    companion object {
        private var instance: BarangDbHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) : BarangDbHelper {
            if (instance == null){
                instance =
                    BarangDbHelper(ctx.applicationContext)
            }
            return instance as BarangDbHelper
        }
    }

    private var tempBarang : ArrayList<Barang> = ArrayList()
    private var tempPenjual = "Temp Penjual"

    override fun onCreate(db: SQLiteDatabase?) {

//        Create Table

        db?.createTable("TABLE_BARANG", true,
            "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "NAMA_BARANG" to TEXT,
            "HARGA_BELI" to INTEGER,
            "FOTO" to TEXT,
            "JUMLAH" to INTEGER,
            "TOTAL" to INTEGER
        )

        db?.createTable("TABLE_PENJUAL", true,
            "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "NAMA_PENJUAL" to TEXT,
            "NO_TELP" to INTEGER
        )

        db?.createTable("TABLE_PEMBELIAN", true,
            "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "ID_PENJUAL" to INTEGER,
            "NO_TELP" to INTEGER,
            "TGL_PEMBELIAN" to TEXT,
            "TOTAL_PEMBELIAN" to INTEGER,
            FOREIGN_KEY("ID_PENJUAL", "TABLE_PENJUAL", "ID_")
        )

        db?.createTable("TABLE_DETAIL_PEMBELIAN", true,
            "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "ID_PEMBELIAN" to INTEGER,
            "ID_BARANG" to INTEGER,
            "HARGA" to INTEGER,
            "ONGKIR" to INTEGER,
            "JUMLAH" to INTEGER,
            "METODE" to INTEGER,
            FOREIGN_KEY("ID_PEMBELIAN", "TABLE_PEMBELIAN", "ID_"),
            FOREIGN_KEY("ID_BARANG", "TABLE_BARANG", "ID_")
        )

//        Insert Data

        db?.insert(PenjualSkema.TABLE_PENJUAL,
            PenjualSkema.NAMA_PENJUAL to "Penjual 1",
            PenjualSkema.NO_TELP to 1234
        )

        db?.insert(BarangSkema.TABLE_BARANG,
            BarangSkema.NAMA_BARANG to "Barang Nomor 1",
            BarangSkema.HARGA_BELI to 1234,
            BarangSkema.FOTO to "https://cdn.pixabay.com/photo/2014/04/03/10/55/t-shirt-311732_1280.png",
            BarangSkema.JUMLAH to 12,
            BarangSkema.TOTAL to 13
        )

        db?.insert(PembelianSkema.TABLE_PEMBELIAN,
            PembelianSkema.ID_PENJUAL to 1,
            PembelianSkema.TGL_PEMBELIAN to "01012001",
            PembelianSkema.TOTAL_PEMBELIAN to 1001
        )
        db?.insert(PembelianSkema.TABLE_PEMBELIAN,
            PembelianSkema.ID_PENJUAL to 1,
            PembelianSkema.TGL_PEMBELIAN to "01012001",
            PembelianSkema.TOTAL_PEMBELIAN to 1002
        )
        db?.insert(PembelianSkema.TABLE_PEMBELIAN,
            PembelianSkema.ID_PENJUAL to 1,
            PembelianSkema.TGL_PEMBELIAN to "01012001",
            PembelianSkema.TOTAL_PEMBELIAN to 1003
        )

        db?.insert(DetailPembelianSkema.TABLE_DETAIL_PEMBELIAN,
            DetailPembelianSkema.ID_PEMBELIAN to 1,
            DetailPembelianSkema.ID_BARANG to 1,
            DetailPembelianSkema.HARGA to 235,
            DetailPembelianSkema.JUMLAH to 5,
            DetailPembelianSkema.ONGKIR to 100,
            DetailPembelianSkema.METODE to 1
        )

//        db?.insert(BarangSkema.TABLE_BARANG,
//            BarangSkema.NAMA_BARANG to "Barang Nomor 2",
//            BarangSkema.HARGA_BELI to 1234,
//            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
//        )
//        db?.insert(BarangSkema.TABLE_BARANG,
//            BarangSkema.NAMA_BARANG to "Barang Nomor 3",
//            BarangSkema.HARGA_BELI to 1234,
//            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
//        )
//        db?.insert(BarangSkema.TABLE_BARANG,
//            BarangSkema.NAMA_BARANG to "Barang Nomor 4",
//            BarangSkema.HARGA_BELI to 1234,
//            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
//        )
//        db?.insert(BarangSkema.TABLE_BARANG,
//            BarangSkema.NAMA_BARANG to "Barang Nomor 7",
//            BarangSkema.HARGA_BELI to 1234,
//            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
//        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable("TABLE_BARANG", true)
        onCreate(db)
    }

    override fun getBarang(): Cursor {
        val db = this.readableDatabase
        val retVal = db.rawQuery("SELECT * FROM TABLE_BARANG",null)

        return retVal
    }

    override fun setBarangFromRemote(dataBarang: List<Barang>) {
        val db = this.readableDatabase
        for (barang in dataBarang) {
            db?.insert(BarangSkema.TABLE_BARANG,
                BarangSkema.NAMA_BARANG to barang.namaBarang,
                BarangSkema.HARGA_BELI to barang.hargaBeli,
                BarangSkema.FOTO to barang.foto,
                BarangSkema.JUMLAH to barang.jumlah,
                BarangSkema.TOTAL to barang.total
            )
        }
    }


    val Context.db : BarangDbHelper
        get() = BarangDbHelper.getInstance(applicationContext)

    override fun insertTempBarang(dataBarang: Barang){
        tempBarang.add(dataBarang)
    }

    override fun getTempBarang(): ArrayList<Barang> {
        return tempBarang
    }

    override fun setTempBarang(dataBarang: ArrayList<Barang>) {
        tempBarang = dataBarang
    }

    override fun getPenjual(): Cursor {
        val db = this.readableDatabase
        val retVal = db.rawQuery("SELECT * FROM TABLE_PENJUAL",null)

        return retVal
    }

    override fun setTempPenjual(dataPenjual: String) {
        tempPenjual = dataPenjual
    }

    override fun getTempPenjual(): String {
        return tempPenjual
    }

    override fun getPembelian(): Cursor {
        val db = this.readableDatabase
        val retVal = db.rawQuery("SELECT * FROM TABLE_PEMBELIAN",null)

        return retVal
    }


}