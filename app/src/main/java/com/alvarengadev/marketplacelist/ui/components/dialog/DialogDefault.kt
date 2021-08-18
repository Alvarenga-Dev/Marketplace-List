package com.alvarengadev.marketplacelist.ui.components.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.alvarengadev.marketplacelist.databinding.DialogDefaultItemBinding

class DialogDefault : DialogFragment() {

    private lateinit var dialog: AlertDialog
    private lateinit var title: String
    private lateinit var btnTitleOptionPrimary: String
    private lateinit var btnTitleOptionSecondary: String

    private var onButtonsDialogListener: OnButtonsDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(this.context)
        val binding = DialogDefaultItemBinding.inflate(LayoutInflater.from(this.context))
        builder.setView(binding.root)
        initializerDialog(binding)

        dialog = builder.create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    private fun initializerDialog(binding: DialogDefaultItemBinding) = binding.apply {
        tvTitleDialog.text = title
        btnOptionPrimary.apply {
            text = btnTitleOptionPrimary
            setOnClickListener {
                onButtonsDialogListener?.setOnClickListenerButtonPrimary()
            }
        }
        btnOptionSecondary.apply {
            text = btnTitleOptionSecondary
            setOnClickListener {
                onButtonsDialogListener?.setOnClickListenerButtonSecondary()
            }
        }
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setBtnTitleOptionPrimary(btnTitleOptionPrimary: String) {
        this.btnTitleOptionPrimary = btnTitleOptionPrimary
    }

    fun setBtnTitleOptionSecondary(btnTitleOptionSecondary: String) {
        this.btnTitleOptionSecondary = btnTitleOptionSecondary
    }

    fun setOnButtonPrimaryDialogListener(onButtonsDialogListener: OnButtonsDialogListener) {
        this.onButtonsDialogListener = onButtonsDialogListener
    }

    companion object {
        fun getInstance(
            title: String,
            btnTitleOptionPrimary: String,
            btnTitleOptionSecondary: String
        ): DialogDefault {
            val dialogDefault = DialogDefault()
            dialogDefault.apply {
                setTitle(title)
                setBtnTitleOptionPrimary(btnTitleOptionPrimary)
                setBtnTitleOptionSecondary(btnTitleOptionSecondary)
            }
            return dialogDefault
        }
    }
}