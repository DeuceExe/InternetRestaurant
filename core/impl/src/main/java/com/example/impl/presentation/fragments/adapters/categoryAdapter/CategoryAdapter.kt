package com.example.impl.presentation.fragments.adapters.categoryAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemCategoryBinding
import com.example.impl.model.Categories

class CategoryAdapter(
    private val categoryList: List<Categories>,
    private val categoryClickListener: (Int) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {

    private lateinit var binding: ItemCategoryBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = ItemCategoryBinding.inflate(inflater, viewGroup, false)
        return CategoryViewHolder(binding, categoryClickListener)
    }

    override fun onBindViewHolder(viewHolder: CategoryViewHolder, position: Int) {
        viewHolder.bind(categoryList[position])
    }

    override fun getItemCount() = categoryList.size
}