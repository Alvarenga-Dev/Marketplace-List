package com.alvarengadev.marketplacelist.ui.fragments.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alvarengadev.marketplacelist.data.models.Item
import com.alvarengadev.marketplacelist.databinding.MyItemCartBinding

class CartAdapter(
   private val listItems: ArrayList<Item>
) : RecyclerView.Adapter<CartViewHolder>() {

    private var onClickItemListener: OnClickItemListener? = null

    fun setOnClickItemListener(onClickItemListener: OnClickItemListener) {
        this.onClickItemListener = onClickItemListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = MyItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding, listItems, onClickItemListener)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}