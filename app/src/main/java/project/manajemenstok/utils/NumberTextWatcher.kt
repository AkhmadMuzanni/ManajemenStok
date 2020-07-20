package project.manajemenstok.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.util.*


open class NumberTextWatcher(editText: EditText): TextWatcher{
    var df = DecimalFormat("#,###.##", DecimalFormatSymbols(Locale("id", "ID")))
//    df!!.setDecimalSeparatorAlwaysShown(true)
    var dfnd = DecimalFormat("#,###", DecimalFormatSymbols(Locale("id", "ID")))
    var et = editText
    var hasFractionalPart = false

    override fun afterTextChanged(s: Editable) {
        et!!.removeTextChangedListener(this)

        try {
            val inilen: Int
            val endlen: Int
            inilen = et!!.text!!.length
            val v = s.toString()
                .replace(df!!.decimalFormatSymbols.groupingSeparator.toString(), "")
            val n = df!!.parse(v)
            val cp = et!!.selectionStart
            if (hasFractionalPart) {
                et!!.setText(df!!.format(n))
            } else {
                et!!.setText(dfnd!!.format(n))
            }
            endlen = et!!.text!!.length
            val sel = cp + (endlen - inilen)
            if (sel > 0 && sel <= et!!.text!!.length) {
                et!!.setSelection(sel)
            } else {
                // place cursor at the end?
                et!!.setSelection(et!!.text!!.length - 1)
            }
        } catch (nfe: NumberFormatException) {
            // do nothing?
        } catch (e: ParseException) {
            // do nothing?
        }

        et!!.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int,
                                   count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int,
                               before: Int, count: Int) {
        hasFractionalPart = s.contains(df!!.decimalFormatSymbols.decimalSeparator.toString())
    }

}