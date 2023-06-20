package com.example.impl.presentation.fragments.adapters.bucketAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemBucketBinding
import com.example.impl.model.Dishes

class BucketAdapter(
    private val dishBucketList: List<Dishes>
) : RecyclerView.Adapter<BucketViewHolder>() {

    private lateinit var binding: ItemBucketBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BucketViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = ItemBucketBinding.inflate(inflater, viewGroup, false)
        return BucketViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: BucketViewHolder, position: Int) {
        viewHolder.bind(dishBucketList[position])
    }

    override fun getItemCount() = dishBucketList.size
}