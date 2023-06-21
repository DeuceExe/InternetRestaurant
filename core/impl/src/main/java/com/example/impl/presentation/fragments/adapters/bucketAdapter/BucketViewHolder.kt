package com.example.impl.presentation.fragments.adapters.bucketAdapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.impl.databinding.ItemBucketBinding
import com.example.impl.model.Dishes

class BucketViewHolder(
    private val binding: ItemBucketBinding,
    private val dishCostObserver: (String, Int) -> Unit,
    private val itemRemoveObserver: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var totalCost = 0
    private var counter = 1

    fun bind(item: Dishes) {
        with(binding) {
            Glide.with(itemView)
                .load(item.image_url)
                .into(imageBucketDish)
            tvBucketDish.text = item.name
            tvBucketPrice.text = "${item.price} ₽"
            tvBucketWeight.text = " - ${item.weight}г"

            checkDishPrice(item.price)
            imageDecrease.setOnClickListener {
                counter--
                tvCounter.text = counter.toString()
                if (counter == 0) {
                    itemRemoveObserver(item.name)
                } else {
                    binding.tvBucketWeight.text = " - ${item.weight * counter}г"
                    dishCostObserver(item.name, checkDishPrice(item.price))
                }
            }

            imageAdd.setOnClickListener {
                counter++
                tvCounter.text = counter.toString()
                binding.tvBucketWeight.text = " - ${item.weight * counter}г"
                dishCostObserver(item.name, checkDishPrice(item.price))
            }
        }
    }

    private fun checkDishPrice(dishPrice: Int): Int {
        totalCost = dishPrice * counter
        binding.tvBucketPrice.text = "$totalCost ₽"
        return totalCost
    }
}