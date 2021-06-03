package com.alvarengadev.marketplacelist.ui.fragments.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.data.models.Item
import com.alvarengadev.marketplacelist.databinding.MyItemCartBinding
import com.alvarengadev.marketplacelist.utils.TextFormatter

class CartViewHolder(
    private val binding: MyItemCartBinding,
    private val listItems: ArrayList<Item>,
    private val onClickItemListener: OnClickItemListener?
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            val positionRcy = adapterPosition
            if (positionRcy != RecyclerView.NO_POSITION) {
                onClickItemListener?.setOnClickItemListener(listItems[positionRcy])
            }
        }
    }

    fun bind(item: Item) = binding.apply {
        item.apply {
            tvNameItem.text = name
            tvQuantityItem.text = itemView.context.getString(R.string.title_quantity_item_with_value, quantity.toString())
            tvValueItem.text = TextFormatter.setCurrency(value)
        }
    }
}