package com.alvarengadev.marketplacelist.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.databinding.DialogDefaultItemBinding
import com.alvarengadev.marketplacelist.utils.extensions.visibilityWithoutAnimation

class PermissionsDialog(
    private val acceptNotification: () -> Unit,
    private val recuseNotification: () -> Unit
) : DialogFragment() {

    private lateinit var dialog: AlertDialog

    private val binding by lazy {
        DialogDefaultItemBinding.inflate(layoutInflater)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.context)
        builder.setView(binding.root)
        initializerDialog(binding)

        dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    private fun initializerDialog(binding: DialogDefaultItemBinding) = binding.apply {

        tvTitleDialog.text = getString(R.string.dialog_permission_title)
        tvBodyDialog.apply {
            visibilityWithoutAnimation()
            text = getString(R.string.dialog_permission_body)
        }

        btnOptionPrimary.apply {
            text = getString(R.string.dialog_permission_button_accept)
            setOnClickListener {
                acceptNotification()
                dialog.dismiss()
            }
        }

        btnOptionSecondary.apply {
            text = getString(R.string.dialog_permission_button_recuse)
            setOnClickListener {
                recuseNotification()
                dialog.dismiss()
            }
        }
    }
}
