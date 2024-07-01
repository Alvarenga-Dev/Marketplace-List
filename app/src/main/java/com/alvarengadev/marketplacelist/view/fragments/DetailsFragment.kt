package com.alvarengadev.marketplacelist.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.databinding.FragmentDetailsBinding
import com.alvarengadev.marketplacelist.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private val detailsViewModel: DetailsViewModel by viewModels()

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

        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }

        detailsViewModel.getItemFromDatabase(args.itemId)
        listenToRegistrationViewModelEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun listenToRegistrationViewModelEvents() = binding.apply {
        with(detailsViewModel) {
            registrationStateEvent.observe(viewLifecycleOwner) { registrationState ->
                if (registrationState is DetailsViewModel.DetailsState.SuccessGetItem) {
                    btnEditDetails.setOnClickListener {
                        val directions = DetailsFragmentDirections.actionDetailsFragmentToAddFragment(
                            registrationState.id
                        )

                        findNavController().navigate(directions)
                    }

                    registrationState.apply {
                        tvNameItemDetails.text = name
                        tvValueDetails.text = getString(R.string.text_value_details, value)
                        tvQuantityDetails.text = getString(R.string.title_quantity_item_with_value, quantity)
                    }
                }
            }
        }
    }
}
