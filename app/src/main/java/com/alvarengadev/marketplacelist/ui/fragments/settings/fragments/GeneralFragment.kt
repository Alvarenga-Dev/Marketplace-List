package com.alvarengadev.marketplacelist.ui.fragments.settings.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.databinding.FragmentGeneralBinding
import com.alvarengadev.marketplacelist.ui.components.dialog.DialogDefault
import com.alvarengadev.marketplacelist.ui.components.dialog.OnButtonsDialogListener
import com.alvarengadev.marketplacelist.ui.fragments.settings.adapter.OnClickItemListener
import com.alvarengadev.marketplacelist.ui.fragments.settings.adapter.SettingsOptionsAdapter
import com.alvarengadev.marketplacelist.utils.Constants
import com.alvarengadev.marketplacelist.utils.Parses
import com.alvarengadev.marketplacelist.utils.PreferencesManager
import com.alvarengadev.marketplacelist.utils.enums.TypeOptionSettings
import com.alvarengadev.marketplacelist.utils.settings.SettingsUtils

class GeneralFragment : Fragment() {

    private var preferencesManager: PreferencesManager? = null
    private var _binding: FragmentGeneralBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeneralBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesManager = PreferencesManager.instance
        initComponents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initComponents() = binding.apply {
        val settingsUtils = SettingsUtils()
        settingsUtils.getInstance(context)

        val adapterListSettings = SettingsOptionsAdapter()

        adapterListSettings.apply {
            submitList(settingsUtils.getListSettingsGeneral())
            setOnClickItemListener(object : OnClickItemListener {
                override fun setOnClickItemListener(typeOptionSettings: TypeOptionSettings) {
                    when (typeOptionSettings) {
                        TypeOptionSettings.GENERAL_CURRENCY -> {
                            val dialog = DialogDefault.getInstance(
                                getString(R.string.dialog_currency_title),
                                getString(R.string.dialog_currency_option_dollar_button),
                                getString(R.string.dialog_currency_option_real_button)
                            )
                            dialog.setOnButtonPrimaryDialogListener(object :
                                OnButtonsDialogListener {
                                override fun setOnClickListenerButtonPrimary() {
                                    dismissDialog(
                                        dialog,
                                        Constants.CURRENCY_EN,
                                        adapterListSettings
                                    )
                                }

                                override fun setOnClickListenerButtonSecondary() {
                                    dismissDialog(
                                        dialog,
                                        Constants.CURRENCY_BR,
                                        adapterListSettings
                                    )
                                }
                            })
                            dialog.show(childFragmentManager, Constants.DIALOG_CURRENCY)
                        }
                        else -> {
                        }
                    }
                }
            })

            binding.rcySettingsGeneral.adapter = adapterListSettings
        }

        toolbarSettingsGeneral.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun dismissDialog(
        dialogDefault: DialogDefault,
        currency: String,
        adapterListSettings: SettingsOptionsAdapter
    ) {
        preferencesManager?.setCurrency(currency)
        dialogDefault.dismiss()
        adapterListSettings.updateItem(TypeOptionSettings.GENERAL_CURRENCY, if (Parses.isLocaleBrazil()) getString(R.string.item_general_option_description_real) else getString(R.string.item_general_option_description_dollar))
    }
}