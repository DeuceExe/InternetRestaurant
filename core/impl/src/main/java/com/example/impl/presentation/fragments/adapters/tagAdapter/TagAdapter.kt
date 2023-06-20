package com.example.impl.presentation.fragments.adapters.tagAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemTagBinding

class TagAdapter(
    private val items: List<String>
) :
    RecyclerView.Adapter<TagViewHolder>() {

    private lateinit var binding: ItemTagBinding
    private var selectedPosition = 0

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TagViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = ItemTagBinding.inflate(inflater, viewGroup, false)
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: TagViewHolder, position: Int) {
        val isSelected = position == selectedPosition
        viewHolder.bind(items[position], isSelected)
    }

    override fun getItemCount() = items.size
}
