package com.alvarengadev.marketplacelist.ui.fragments.settings.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alvarengadev.marketplacelist.databinding.FragmentGeneralBinding
import com.alvarengadev.marketplacelist.ui.fragments.settings.adapter.OnClickItemListener
import com.alvarengadev.marketplacelist.ui.fragments.settings.adapter.SettingsOptionsAdapter
import com.alvarengadev.marketplacelist.utils.CurrencyAppUtils
import com.alvarengadev.marketplacelist.utils.enums.TypeOptionSettings
import com.alvarengadev.marketplacelist.utils.settings.SettingsUtils

class GeneralFragment : Fragment() {

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
        initComponents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initComponents() = binding.apply {
        val settingsUtils = SettingsUtils()
        settingsUtils.getInstance(context)

        val adapterListSettings = SettingsOptionsAdapter(settingsUtils.getListSettingsGeneral(
            theme = "Light",
            currency = CurrencyAppUtils.getCurrency()
        ))
        adapterListSettings.setOnClickItemListener(object : OnClickItemListener {
            override fun setOnClickItemListener(typeOptionSettings: TypeOptionSettings) {
                when (typeOptionSettings) {
                    TypeOptionSettings.GENERAL_THEME -> {

                    }
                    TypeOptionSettings.GENERAL_CURRENCY -> {

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