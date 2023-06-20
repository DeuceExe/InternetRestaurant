package com.example.impl.presentation.fragments.adapters.dishesAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemDishBinding
import com.example.impl.model.Dishes

class DishesAdapter(
    private val dishesList: List<Dishes>,
    private val onDishClickListener: (Int) -> Unit
) : RecyclerView.Adapter<DishesViewHolder>() {

    private lateinit var binding: ItemDishBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DishesViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = ItemDishBinding.inflate(inflater, viewGroup, false)
        return DishesViewHolder(binding, onDishClickListener)
    }

    override fun onBindViewHolder(viewHolder: DishesViewHolder, position: Int) {
            viewHolder.bind(dishesList[position])
    }

    override fun getItemCount() = dishesList.size
}

enum class TagDishes(val tagName: String){

    ALL_MENU("Все меню"),
    SALAD("Салаты"),
    RICE("С рисом"),
    FISH("С рыбой")
}