package com.alvarengadev.marketplacelist.ui.fragments.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.data.model.ItemModel
import com.alvarengadev.marketplacelist.databinding.ItemCartBinding
import com.alvarengadev.marketplacelist.ui.fragments.cart.dialog.delete.DeleteItemDialog
import com.alvarengadev.marketplacelist.ui.fragments.cart.dialog.delete.DeleteItemInterface
import com.alvarengadev.marketplacelist.utils.TextFormatter

class CartAdapter : ListAdapter<ItemModel, CartAdapter.CartViewHolder>(DIFF_CALLBACK) {

    private var onClickItemListener: OnClickItemListener? = null
    private var observerListEmpty: ObserverListEmpty? = null
    private var supportFragmentManager: FragmentManager? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding, onClickItemListener)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            supportFragmentManager,
            (position + 1).toString()
        )
    }

    fun deleteItem(itemId: Int) {
        val newList = currentList.toMutableList()
        val item = newList.first { item -> itemId == item.id }
        val position = newList.indexOf(item)
        newList.removeAt(position)
        submitList(newList)

        var newTotal = 0.0
        newList.map {
            newTotal += (it.value * it.quantity)
        }

        notifyItemRemoved(position)
        notifyItemRangeChanged(position, newList.size)
        observerListEmpty?.observer(newTotal, newList.isEmpty())
    }

    inner class CartViewHolder(
        private val binding: ItemCartBinding,
        private val onClickItemListener: OnClickItemListener?
    ) : RecyclerView.ViewHolder(binding.root), DeleteItemInterface {
        fun bind(
            itemModel: ItemModel,
            supportFragmentManager: FragmentManager?,
            position: String
        ) = binding.apply {
            itemModel.apply {
                tvNameItem.text = name
                tvQuantityItem.text = itemView
                    .context
                    .getString(
                        R.string.title_quantity_item_with_value,
                        quantity.toString()
                    )
                tvValueItem.text = itemView
                    .context
                    .getString(
                        R.string.text_value_details,
                        TextFormatter.setCurrency(itemView.context, value)
                    )

                ibDeleteItem.setOnClickListener {
                    val deleteItemDialog = DeleteItemDialog()
                    deleteItemDialog.setInstance(id, this@CartViewHolder)
                    supportFragmentManager?.let { deleteItemDialog.show(it, "") }
                }

                root.contentDescription = itemView.context
                    .getString(
                        R.string.content_description_item_cart,
                        position,
                        name,
                        TextFormatter.setCurrency(itemView.context, value),
                        quantity.toString()
                    )

                itemView.setOnClickListener {
                    val positionRcy = adapterPosition
                    if (positionRcy != RecyclerView.NO_POSITION) {
                        id?.let { id ->
                            onClickItemListener?.setOnClickItemListener(
                                id
                            )
                        }
                    }
                }
            }
        }

        override fun notifyItemDelete(itemId: Int) {
            deleteItem(itemId)
        }
    }

    fun setOnClickItemListener(onClickItemListener: OnClickItemListener) {
        this.onClickItemListener = onClickItemListener
    }

    fun observerListEmpty(observerListEmpty: ObserverListEmpty) {
        this.observerListEmpty = observerListEmpty
    }

    fun setSupportFragmentManager(supportFragmentManager: FragmentManager) {
        this.supportFragmentManager = supportFragmentManager
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemModel>() {
            override fun areItemsTheSame(oldItemModel: ItemModel, newItemModel: ItemModel): Boolean {
                return oldItemModel.id == newItemModel.id
            }

            override fun areContentsTheSame(oldItemModel: ItemModel, newItemModel: ItemModel): Boolean {
                return oldItemModel.id == newItemModel.id
            }
        }
    }
}
