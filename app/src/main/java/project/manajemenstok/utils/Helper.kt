package project.manajemenstok.utils

import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.SearchView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import project.manajemenstok.utils.Constants.Companion.BUCKER_FOLDER
import project.manajemenstok.utils.Constants.Companion.USER_ID
import java.text.NumberFormat
import java.util.*

class Helper {
    companion object {
        fun getAngka(string: String): Int {
            val idLocale = Locale("id", "ID")
            val nf = NumberFormat.getNumberInstance(idLocale)
            return nf.parse(string).toInt()
        }

        fun getFormat(int: Int): String {
            val idLocale = Locale("id", "ID")
            val nf = NumberFormat.getNumberInstance(idLocale)
            return nf.format(int)
        }

        fun setFontSizeSearchView(searchView: SearchView, size: Float){
            val linearLayout1 = searchView.getChildAt(0) as LinearLayout
            val linearLayout2 = linearLayout1.getChildAt(2) as LinearLayout
            val linearLayout3 = linearLayout2.getChildAt(1) as LinearLayout
            val autoComplete = linearLayout3.getChildAt(0) as AutoCompleteTextView
            autoComplete.textSize = 14f
        }

        fun getTextViewSearchView(searchView: SearchView): AutoCompleteTextView{
            val linearLayout1 = searchView.getChildAt(0) as LinearLayout
            val linearLayout2 = linearLayout1.getChildAt(2) as LinearLayout
            val linearLayout3 = linearLayout2.getChildAt(1) as LinearLayout
            val autoComplete = linearLayout3.getChildAt(0) as AutoCompleteTextView
            return autoComplete
        }

        fun getDbReference(query: String): DatabaseReference {
            val database = Firebase.database
            return database.getReference(query).child(USER_ID)
        }

        fun getStorageReference(query: String): StorageReference {
            return Firebase.storage.reference.child(BUCKER_FOLDER).child(query)
        }
    }
}