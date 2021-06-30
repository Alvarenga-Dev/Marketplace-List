package com.alvarengadev.marketplacelist.ui.fragments.cart.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.alvarengadev.marketplacelist.data.models.Item
import com.alvarengadev.marketplacelist.databinding.DialogDeleteItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteItemDialog : DialogFragment() {

    private val deleteItemViewModel: DeleteItemViewModel by viewModels()
    private var deleteItemInterface: DeleteItemInterface? = null
    private var item: Item? = null
    private lateinit var dialog: AlertDialog

    fun setInstance(item: Item, deleteItemInterface: DeleteItemInterface) {
        this.item = item
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

        return dialog
    }

    private fun initializerDialog(binding: DialogDeleteItemBinding) =
        binding.apply {
            btnConfirmDialogDelete.setOnClickListener {
                deleteItemViewModel.apply {
                    item?.let { item -> deleteItem(item) }
                    isDelete.observeForever { isDelete ->
                        if (isDelete) {
                            item?.let { item -> deleteItemInterface?.notifyItemDelete(item) }
                            closeDialog()
                        }
                    }
                }
            }

            btnCancelDialogDelete.setOnClickListener {
                closeDialog()
            }
        }

    private fun closeDialog() = dialog.dismiss()
}