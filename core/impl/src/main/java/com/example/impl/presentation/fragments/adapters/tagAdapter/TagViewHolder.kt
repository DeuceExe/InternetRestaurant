package com.example.impl.presentation.fragments.adapters.tagAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemTagBinding

class TagViewHolder(
    val binding: ItemTagBinding,
    private val onTagClickListener: (String, Boolean) -> Unit
    ) :
    RecyclerView.ViewHolder(binding.root) {

    private var tagList = arrayListOf<String>()

    fun bind(item: String, isSelected: Boolean) {
        binding.btnTag.isChecked = isSelected
        binding.btnTag.text = item

        binding.btnTag.setOnClickListener {
            binding.btnTag.text = item

            onTagClickListener.invoke(item, binding.btnTag.isChecked)
            tagList.add(item)
        }
    }
}