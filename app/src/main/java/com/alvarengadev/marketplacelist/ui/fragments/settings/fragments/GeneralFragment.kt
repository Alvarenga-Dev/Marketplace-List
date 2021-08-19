package com.alvarengadev.marketplacelist.ui.fragments.settings.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alvarengadev.marketplacelist.databinding.FragmentGeneralBinding
import com.alvarengadev.marketplacelist.ui.components.dialog.DialogDefault
import com.alvarengadev.marketplacelist.ui.components.dialog.OnButtonsDialogListener
import com.alvarengadev.marketplacelist.ui.fragments.settings.adapter.OnClickItemListener
import com.alvarengadev.marketplacelist.ui.fragments.settings.adapter.SettingsOptionsAdapter
import com.alvarengadev.marketplacelist.utils.Constants
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

        val adapterListSettings = SettingsOptionsAdapter(settingsUtils.getListSettingsGeneral())
        adapterListSettings.setOnClickItemListener(object : OnClickItemListener {
            override fun setOnClickItemListener(typeOptionSettings: TypeOptionSettings) {
                when (typeOptionSettings) {
                    TypeOptionSettings.GENERAL_CURRENCY -> {
                        val dialog = DialogDefault.getInstance("Escolha uma das moedas", "Dólar", "Real")
                        dialog.setOnButtonPrimaryDialogListener(object : OnButtonsDialogListener {
                            override fun setOnClickListenerButtonPrimary() {
                                preferencesManager?.setCurrency(Constants.CURRENCY_EN)
                                dialog.dismiss()
                            }

                            override fun setOnClickListenerButtonSecondary() {
                                preferencesManager?.setCurrency(Constants.CURRENCY_BR)
                                dialog.dismiss()
                            }
                        })
                        dialog.show(childFragmentManager, Constants.DIALOG_CURRENCY)
                    }
                    else -> TODO()
                }
            }
        })

        binding.rcySettingsGeneral.adapter = adapterListSettings

        toolbarSettingsGeneral.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}