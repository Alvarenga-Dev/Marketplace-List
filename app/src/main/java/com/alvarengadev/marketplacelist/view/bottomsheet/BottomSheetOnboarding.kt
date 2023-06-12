package com.alvarengadev.marketplacelist.view.bottomsheet

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.databinding.ComponentBottomSheetOnboardingBinding
import com.alvarengadev.marketplacelist.view.bottomsheet.`interface`.ButtonGotItClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetOnboarding : BottomSheetDialogFragment() {

    private var _binding: ComponentBottomSheetOnboardingBinding? = null
    private val binding get() = _binding!!

    private var buttonGotItClickListener: ButtonGotItClickListener? = null

    fun setButtonGotItClickListener(buttonGotItClickListener: ButtonGotItClickListener): BottomSheetOnboarding {
        this.buttonGotItClickListener = buttonGotItClickListener
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetWithoutDraggable)
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        _binding = ComponentBottomSheetOnboardingBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(false)
        setupComponents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupComponents() = binding.apply {
        btnGotItOnboarding.setOnClickListener {
            buttonGotItClickListener?.onClick()
        }
    }
}
