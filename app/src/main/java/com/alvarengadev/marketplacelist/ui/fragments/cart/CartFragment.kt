package com.alvarengadev.marketplacelist.ui.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.databinding.FragmentCartBinding
import com.alvarengadev.marketplacelist.ui.fragments.cart.adapter.CartAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartViewModel.getListItems()
        initComponents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initComponents() {
        binding.footerCart
            .setTextButton("Novo produto")
            .setActionButton {
                findNavController().navigate(R.id.action_cartFragment_to_addFragment)
            }

        binding.apply {
            cartViewModel.listItems.observeForever { listItems ->
                if (listItems.isEmpty()) {
                    showList(false)
                } else {
                    showList(true)
                    rcyCartItem.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = CartAdapter(listItems)
                    }
                }

                var totalValue = 0.0

                for (item in listItems) {
                    totalValue += (item.value * item.quantity)
                }
                footerCart.setCartValue(totalValue)
            }
        }
    }

    private fun showList(isShow: Boolean) = binding.apply {
        if (isShow) {
            rcyCartItem.visibility = View.VISIBLE
            containerListEmpty.visibility = View.GONE
        } else {
            rcyCartItem.visibility = View.GONE
            containerListEmpty.visibility = View.VISIBLE
        }
    }

}