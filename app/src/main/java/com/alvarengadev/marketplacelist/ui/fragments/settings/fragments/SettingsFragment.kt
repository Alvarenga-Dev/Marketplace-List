package com.alvarengadev.marketplacelist.ui.fragments.settings.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.databinding.FragmentSettingsBinding
import com.alvarengadev.marketplacelist.ui.fragments.settings.adapter.OnClickItemListener
import com.alvarengadev.marketplacelist.ui.fragments.settings.adapter.SettingsOptionsAdapter
import com.alvarengadev.marketplacelist.utils.enums.TypeOptionSettings
import com.alvarengadev.marketplacelist.utils.settings.SettingsUtils

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
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

        val adapterListSettings = SettingsOptionsAdapter()

        adapterListSettings.apply {
            submitList(settingsUtils.getListSettings())
            setOnClickItemListener(object : OnClickItemListener {
                override fun setOnClickItemListener(typeOptionSettings: TypeOptionSettings) {
                    when (typeOptionSettings) {
                        TypeOptionSettings.SETTINGS_GENERAL -> {
                            findNavController().navigate(R.id.action_settingsFragment_to_generalFragment)
                        }
                        TypeOptionSettings.SETTINGS_ABOUT -> {
                            findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
                        }
                        else -> {
                        }
                    }
                }
            })
        }

        binding.rcySettings.adapter = adapterListSettings

        toolbarSettings.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}