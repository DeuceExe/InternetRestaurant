package com.example.impl.presentation.fragments.adapters.tagAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemTagBinding

class TagAdapter(
    private val items: List<String>,
    private val onTagClickListener: (ArrayList<String>) -> Unit
) :
    RecyclerView.Adapter<TagViewHolder>() {

    private lateinit var binding: ItemTagBinding
    private var selectedPosition = 0

    private var selectedTags = arrayListOf("Все меню")

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TagViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = ItemTagBinding.inflate(inflater, viewGroup, false)
        return TagViewHolder(binding){tag, isChecked ->
            if(isChecked)
                selectedTags.add(tag)
            else
                selectedTags.remove(tag)
                onTagClickListener.invoke(selectedTags)
        }
    }

    override fun onBindViewHolder(viewHolder: TagViewHolder, position: Int) {
        val isSelected = position == selectedPosition
        viewHolder.bind(items[position], isSelected)
    }

    override fun getItemCount() = items.size
}
