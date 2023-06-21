package com.example.impl.presentation.fragments.adapters.bucketAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemBucketBinding
import com.example.impl.model.Dishes

class BucketAdapter(
    private var dishBucketList: List<Dishes>,
    private val dishCostObserver: (String, Int) -> Unit,
    private val itemRemoveObserver: (String) -> Unit
) : RecyclerView.Adapter<BucketViewHolder>() {

    private lateinit var binding: ItemBucketBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BucketViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = ItemBucketBinding.inflate(inflater, viewGroup, false)
        return BucketViewHolder(binding, dishCostObserver, itemRemoveObserver)
    }

    override fun onBindViewHolder(viewHolder: BucketViewHolder, position: Int) {
        viewHolder.bind(dishBucketList[position])
    }

    override fun getItemCount() = dishBucketList.size

    fun removeItem(itemName: String) {
        val index = dishBucketList.indexOfFirst { it.name == itemName }
        if (index != -1) {
            dishBucketList = dishBucketList.toMutableList().apply {
                removeAt(index)
            }
            notifyItemRemoved(index)
        }
    }
}