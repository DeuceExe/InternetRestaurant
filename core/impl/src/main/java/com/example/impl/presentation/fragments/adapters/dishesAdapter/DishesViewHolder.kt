package com.example.impl.presentation.fragments.adapters.dishesAdapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.impl.databinding.ItemDishBinding
import com.example.impl.model.Dishes

class DishesViewHolder(
    private val binding: ItemDishBinding,
    private val onDishClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Dishes) {
        with(binding) {
            Glide.with(itemView)
                .load(item.image_url)
                .into(imageDish)
            tvDish.text = item.name
        }

        itemView.setOnClickListener {
            onDishClickListener.invoke(item.id)
        }
    }
}