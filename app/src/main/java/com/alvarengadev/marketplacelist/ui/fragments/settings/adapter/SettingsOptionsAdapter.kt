package com.alvarengadev.marketplacelist.ui.fragments.settings.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alvarengadev.marketplacelist.databinding.ItemSettingsBinding
import com.alvarengadev.marketplacelist.utils.enums.TypeOptionSettings
import com.alvarengadev.marketplacelist.utils.settings.SettingsUtils

class SettingsOptionsAdapter :
    ListAdapter<SettingsUtils.Options, SettingsOptionsAdapter.SettingsViewHolder>(DIFF_CALLBACK) {

    private var onClickItemListener: OnClickItemListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingsViewHolder {
        val binding = ItemSettingsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SettingsViewHolder(binding, onClickItemListener)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(getItem(position))

        if ((currentList.size - 1) == position) {
            holder.lastPosition()
        }
    }

    fun setOnClickItemListener(onClickItemListener: OnClickItemListener) {
        this.onClickItemListener = onClickItemListener
    }

    fun updateItem(
        typeOptionSettings: TypeOptionSettings,
        currency: String
    ) {
        val newList = currentList.toMutableList()
        val option = newList.first { options -> options.typeOptionSettings == typeOptionSettings }
        option.description = currency

        notifyItemChanged(newList.indexOf(option))
        submitList(newList)
    }

    inner class SettingsViewHolder(
        private val binding: ItemSettingsBinding,
        private val onClickItemListener: OnClickItemListener?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(option: SettingsUtils.Options) = binding.apply {
            with(option) {
                if (title != null) {
                    tvTitleSettings.text = title
                }
                if (description != null) {
                    tvDescriptionSettings.text = description
                }
            }

            itemView.setOnClickListener {
                val positionRcy = adapterPosition
                if (positionRcy != RecyclerView.NO_POSITION) {
                    option.typeOptionSettings?.let { typeOptionSettings ->
                        onClickItemListener?.setOnClickItemListener(typeOptionSettings)
                    }
                }
            }
        }

        fun lastPosition() {
            binding.viewLineSettings.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SettingsUtils.Options>() {
            override fun areItemsTheSame(
                oldItem: SettingsUtils.Options,
                newItem: SettingsUtils.Options
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SettingsUtils.Options,
                newItem: SettingsUtils.Options
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}