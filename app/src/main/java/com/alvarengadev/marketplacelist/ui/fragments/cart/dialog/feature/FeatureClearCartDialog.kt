package com.alvarengadev.marketplacelist.ui.fragments.cart.dialog.feature

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.databinding.DialogNewFeatureItemBinding
import com.alvarengadev.marketplacelist.utils.PreferencesManager

class FeatureClearCartDialog : DialogFragment() {

    private lateinit var dialog: AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(this.context)
        val binding = DialogNewFeatureItemBinding.inflate(LayoutInflater.from(this.context))
        builder.setView(binding.root)
        initializerDialog(binding)

        dialog = builder.create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    private fun closeDialog() {
        dialog.dismiss()
    }

    private fun initializerDialog(binding: DialogNewFeatureItemBinding) = binding.apply {
        tvTitleDialogFeature.text = getString(R.string.dialog_feature_clear_cart_title)
        tvDescriptionDialogFeature.text = getString(R.string.dialog_feature_clear_cart_description)

        btnDialogFeature.apply {
            text = getString(R.string.dialog_feature_clear_cart_button)
            setOnClickListener {
                PreferencesManager.instance?.setViewOnboardingWelcome()
                closeDialog()
            }
        }
    }
}