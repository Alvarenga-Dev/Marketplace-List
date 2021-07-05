package com.alvarengadev.marketplacelist.ui.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.databinding.FragmentDetailsBinding
import com.alvarengadev.marketplacelist.utils.TextFormatter

class FragmentDetails : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: FragmentDetailsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
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

    private fun initComponents() {
        binding.apply {
            ibBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnEditDetails.setOnClickListener {
                val directions = args.item.id?.let { it1 ->
                    FragmentDetailsDirections.actionDetailsFragmentToAddFragment().setItemId(
                        it1
                    )
                }
                if (directions != null) {
                    findNavController().navigate(directions)
                }
            }

            args.item.apply {
                tvNameItemDetails.text = name
                tvValueDetails.text = getString(R.string.text_value_details, TextFormatter.setCurrency(value))
                tvQuantityDetails.text = getString(R.string.title_quantity_item_with_value, quantity.toString())
            }
        }
    }
}