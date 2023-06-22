package com.alvarengadev.marketplacelist.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MoneyTextWatcher(
    private val context: Context,
    private val editText: EditText?
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(editable: Editable) {
        val editText = editText ?: return
        editText.removeTextChangedListener(this)
        val formatted = Parses.parseToCurrency(context, editable.toString())

        editText.setText(formatted)
        editText.setSelection(formatted.length)
        editText.addTextChangedListener(this)
    }
}
