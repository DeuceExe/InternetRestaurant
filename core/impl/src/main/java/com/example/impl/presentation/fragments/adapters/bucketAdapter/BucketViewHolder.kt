package com.example.impl.presentation.fragments.adapters.bucketAdapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.impl.databinding.ItemBucketBinding
import com.example.impl.model.Dishes

class BucketViewHolder(
    private val binding: ItemBucketBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var counter = 0

    fun bind(item: Dishes) {
        with(binding) {
            Glide.with(itemView)
                .load(item.image_url)
                .into(imageBucketDish)
            tvBucketDish.text = item.name
            tvBucketPrice.text = "${item.price} ₽"
            tvBucketWeight.text = " - ${item.weight}г"

            imageDecrease.setOnClickListener {
                if(counter > 0) {
                    counter--
                    tvCounter.text = counter.toString()
                }
            }

            imageAdd.setOnClickListener {
                counter++
                tvCounter.text = counter.toString()
            }
        }
    }
}