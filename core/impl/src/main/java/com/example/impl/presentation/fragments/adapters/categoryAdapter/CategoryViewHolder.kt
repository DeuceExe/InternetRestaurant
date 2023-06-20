package com.example.impl.presentation.fragments.adapters.categoryAdapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.impl.databinding.ItemCategoryBinding
import com.example.impl.model.Categories

class CategoryViewHolder(
    private val binding: ItemCategoryBinding,
    private val categoryClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Categories) {
        with(binding) {
            Glide.with(itemView)
                .load(item.image_url)
                .into(imageCategory)
            tvCategory.text = item.name
        }

        itemView.setOnClickListener {
            categoryClickListener.invoke(item.id)
        }
    }
}