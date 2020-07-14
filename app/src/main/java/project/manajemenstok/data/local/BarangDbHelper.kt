package project.manajemenstok.data.local

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import project.manajemenstok.data.model.Barang

class BarangDbHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "ManajemenStok.db", null, 1),
    BarangLogic {

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

//        Insert Data

//        db?.insert(BarangSkema.TABLE_BARANG,
//            BarangSkema.NAMA_BARANG to "Barang Nomor 1",
//            BarangSkema.HARGA_BELI to 1234,
//            BarangSkema.FOTO to "https://s3.amazonaws.com/uifaces/faces/twitter/brenmurrell/128.jpg"
//        )
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


}