package com.example.impl.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.impl.databinding.DialogDishBinding
import com.example.impl.model.Dishes
import com.example.impl.model.Menu
import com.example.impl.utils.getParcelable
import com.example.impl.utils.putParcelable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogDishBinding

    private var orderList = Menu(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickListener()
    }

    fun setDishInfo(dish: Dishes) {
        with(binding) {
            CoroutineScope(Dispatchers.Main).launch {
                Glide.with(context)
                    .load(dish.image_url)
                    .into(imageSelectedDish)
            }
            tvSelectedDishTitle.text = dish.name
            tvSelectedDishPrice.text = "${dish.price} ₽"
            tvSelectedDishWeight.text = " - ${dish.weight}г"
            tvSelectedDishDescription.text = dish.description
        }

        val orderPrefs = context.getSharedPreferences("orderDetails", MODE_PRIVATE)
        val savedOrderList = orderPrefs?.getParcelable(SHARED_BUNDLE, Menu(arrayListOf()))
        if (savedOrderList?.dishes.isNullOrEmpty()) {
            savedOrderList?.dishes?.add(dish)
        }
        orderList.dishes.add(dish)
    }

    private fun setClickListener() {
        var isFavorite = false

        binding.imageCancel.setOnClickListener {
            dismiss()
        }

        binding.imageFavorite.setOnClickListener {
            isFavorite = if (isFavorite) {
                binding.imageFavorite.setImageResource(com.example.impl.R.drawable.ic_favorite_unselected)
                false
            } else {
                binding.imageFavorite.setImageResource(com.example.impl.R.drawable.ic_favorite_selected)
                true
            }
        }

        binding.btnAddToBucket.setOnClickListener {
            saveOrderList()
        }
    }

    private fun saveOrderList() {
        val orderDetails: SharedPreferences =
            context.getSharedPreferences("orderDetails", MODE_PRIVATE)
        val edit: Editor = orderDetails.edit()
        edit.putParcelable(SHARED_BUNDLE, orderList)
        edit.apply()
    }

    companion object {

        const val SHARED_BUNDLE = "shared_bundle"
    }
}