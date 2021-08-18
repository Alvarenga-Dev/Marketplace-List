package com.alvarengadev.marketplacelist.ui.settings.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.databinding.FragmentAboutBinding
import com.alvarengadev.marketplacelist.ui.settings.adapter.OnClickItemListener
import com.alvarengadev.marketplacelist.ui.settings.adapter.SettingsOptionsAdapter
import com.alvarengadev.marketplacelist.utils.enums.TypeOptionSettings
import com.alvarengadev.marketplacelist.utils.settings.SettingsUtils

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
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

        val adapterListSettings = SettingsOptionsAdapter(settingsUtils.getListSettingsAbout())
        adapterListSettings.setOnClickItemListener(object : OnClickItemListener {
            override fun setOnClickItemListener(typeOptionSettings: TypeOptionSettings) {
                when (typeOptionSettings) {
                    TypeOptionSettings.ABOUT_PRIVACY_POLICY -> {

                    }
                    else -> TODO()
                }
            }
        })

        binding.rcySettingsAbout.adapter = adapterListSettings

        toolbarSettingsAbout.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}