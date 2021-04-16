package com.alvarengadev.marketplacelist.ui.fragments.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.data.models.Item

class CartAdapter(
   // private val listItems: ArrayList<Item>
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.my_item_cart,
                parent,
                false
            )
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 5 //listItems.size
    }
}