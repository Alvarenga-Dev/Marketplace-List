package com.alvarengadev.marketplacelist.ui.fragments.cart.dialog.delete

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.alvarengadev.marketplacelist.databinding.DialogDeleteItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteItemDialog : DialogFragment() {

    private val deleteItemViewModel: DeleteItemViewModel by viewModels()
    private var deleteItemInterface: DeleteItemInterface? = null
    private var itemId: Int? = null
    private lateinit var dialog: AlertDialog

    fun setInstance(itemId: Int?, deleteItemInterface: DeleteItemInterface) {
        this.itemId = itemId
        this.deleteItemInterface = deleteItemInterface
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.context)
        val binding = DialogDeleteItemBinding.inflate(LayoutInflater.from(this.context))
        builder.setView(binding.root)
        initializerDialog(binding)

        dialog = builder.create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        listenToRegistrationViewModelEvents()

        return dialog
    }

    private fun initializerDialog(binding: DialogDeleteItemBinding) = binding.apply {
        btnConfirmDialogDelete.setOnClickListener {
            deleteItemViewModel.apply {
                itemId?.let { deleteItemFromDatabase(it) }
            }
        }

        btnCancelDialogDelete.setOnClickListener {
            closeDialog()
        }
    }

    private fun listenToRegistrationViewModelEvents() {
        deleteItemViewModel.registrationStateEvent.observeForever { registrationState ->
            if (registrationState is DeleteItemViewModel.DeleteState.Result) {
                if (registrationState.isSuccessful) {
                    itemId?.let { deleteItemInterface?.notifyItemDelete(it) }
                    closeDialog()
                }
            }
        }
    }

    private fun closeDialog() = dialog.dismiss()
}