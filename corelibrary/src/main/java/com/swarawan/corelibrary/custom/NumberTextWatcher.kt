@file:JvmName("NumberTextWatcher")
package com.swarawan.corelibrary.custom

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException

/**
 * Created by Rio Swarawn on 5/30/18.
 */
class NumberTextWatcher(private val editText: EditText) : TextWatcher {

    private var df: DecimalFormat? = null
    private var dfnd: DecimalFormat? = null
    private var hasFractionalPart: Boolean = false

    init {
        val decimalSymbol = DecimalFormatSymbols.getInstance().apply {
            decimalSeparator = ','
            groupingSeparator = '.'
        }
        df = DecimalFormat("#,###.##").apply {
            isDecimalSeparatorAlwaysShown = false
            decimalFormatSymbols = decimalSymbol
        }
        dfnd = DecimalFormat("#,###").apply {
            decimalFormatSymbols = decimalSymbol
        }
        hasFractionalPart = false
    }

    override fun afterTextChanged(editable: Editable?) {
        editText.removeTextChangedListener(this)

        try {
            var endlen: Int
            var inilen: Int = editText.text.length

            df?.let {
                val v: String = editable.toString().replace(it.decimalFormatSymbols.groupingSeparator.toString(), "")
                val n: Number = it.parse(v)
                val cp: Int = editText.selectionStart

                when (hasFractionalPart) {
                    true -> editText.setText(it.format(n))
                    false -> editText.setText(dfnd?.format(n))
                }

                endlen = editText.text.length
                val sel: Int = (cp + (endlen - inilen))
                when (sel > 0 && sel <= editText.text.length) {
                    true -> editText.setSelection(sel)
                    false -> editText.setSelection(editText.text.length - 1)
                }
            }

        } catch (nfe: NumberFormatException) {
        } catch (e: ParseException) {
        }

        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
        df?.let {
            hasFractionalPart = charSequence.toString().contains(it.decimalFormatSymbols.decimalSeparator.toString())
        }
    }
}