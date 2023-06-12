package com.alvarengadev.marketplacelist.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.alvarengadev.marketplacelist.databinding.DialogDeleteItemBinding
import com.alvarengadev.marketplacelist.view.dialog.interfaces.DeleteItemInterface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteItemDialog : DialogFragment() {

    private val deleteItemViewModel: DeleteItemViewModel by viewModels()
    private var deleteItemInterface: DeleteItemInterface? = null
    private var itemId: Int? = null
    private lateinit var dialog: AlertDialog

    private val binding by lazy {
        DialogDeleteItemBinding.inflate(layoutInflater)
    }

    fun setInstance(itemId: Int?, deleteItemInterface: DeleteItemInterface) {
        this.itemId = itemId
        this.deleteItemInterface = deleteItemInterface
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.context)
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
                itemId?.let { deleteItemWithId(it) }
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
