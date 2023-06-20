package com.example.impl.presentation.fragments.adapters.tagAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemTagBinding

class TagViewHolder(val binding: ItemTagBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var tagList = arrayListOf<String>()

    fun bind(item: String, isSelected: Boolean) {
        binding.btnTag.isChecked = isSelected
        binding.btnTag.text = item

        binding.btnTag.setOnClickListener {
            binding.btnTag.text = item

            tagList.add(item)
        }
    }
}