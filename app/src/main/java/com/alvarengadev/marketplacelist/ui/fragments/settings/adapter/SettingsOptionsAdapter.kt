package com.alvarengadev.marketplacelist.ui.fragments.settings.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alvarengadev.marketplacelist.databinding.ItemSettingsBinding
import com.alvarengadev.marketplacelist.utils.settings.SettingsUtils

class SettingsOptionsAdapter(
    private val listOptions: ArrayList<SettingsUtils.Options>
) : RecyclerView.Adapter<SettingsOptionsAdapter.SettingsViewHolder>() {

    private var onClickItemListener: OnClickItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val binding = ItemSettingsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SettingsViewHolder(binding, onClickItemListener, listOptions)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(listOptions[position])

        if ((listOptions.size - 1) == position) {
            holder.lastPosition()
        }
    }

    override fun getItemCount(): Int {
        return listOptions.size
    }

    fun setOnClickItemListener(onClickItemListener: OnClickItemListener) {
        this.onClickItemListener = onClickItemListener
    }

    inner class SettingsViewHolder(
        private val binding: ItemSettingsBinding,
        private val onClickItemListener: OnClickItemListener?,
        private val listOptions: ArrayList<SettingsUtils.Options>
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val positionRcy = adapterPosition
                if (positionRcy != RecyclerView.NO_POSITION) {
                    listOptions[positionRcy].typeOptionSettings?.let { typeOptionSettings ->
                        onClickItemListener?.setOnClickItemListener(typeOptionSettings)
                    }
                }
            }
        }

        fun bind(option: SettingsUtils.Options) = binding.apply {
            with(option) {
                if (title != null) {
                    tvTitleSettings.text = title
                }
                if (description != null) {
                    tvDescriptionSettings.text = description
                }
            }
        }

        fun lastPosition() {
            binding.viewLineSettings.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}