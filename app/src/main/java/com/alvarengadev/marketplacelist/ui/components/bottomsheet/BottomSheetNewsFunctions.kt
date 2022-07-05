package com.alvarengadev.marketplacelist.ui.components.bottomsheet

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.databinding.ComponentBottomSheetFunctionsBinding
import com.alvarengadev.marketplacelist.utils.PreferencesManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetNewsFunctions : BottomSheetDialogFragment() {

    private var _binding: ComponentBottomSheetFunctionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetWithoutDraggable)
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        _binding = ComponentBottomSheetFunctionsBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(false)
        setupComponents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupComponents() = binding.apply {
        btnGotItNews.setOnClickListener {
            PreferencesManager.instance?.setViewOnboardingNewsFunctions()
            dismiss()
        }
    }
}