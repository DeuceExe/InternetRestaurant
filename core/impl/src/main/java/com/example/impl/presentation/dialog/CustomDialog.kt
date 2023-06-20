package com.example.impl.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.impl.databinding.DialogDishBinding
import com.example.impl.model.Dishes
import com.example.impl.rest.IOrderInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class CustomDialog(context: Context) : Dialog(context), KoinComponent {

    private lateinit var binding: DialogDishBinding

    private val orderList: MutableList<Dishes> = mutableListOf()

    private lateinit var orderListener: IOrderInfo

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
        orderList.add(dish)
    }

    private fun setClickListener() {
        var isFavorite = false

        binding.cardViewCancel.setOnClickListener {
            dismiss()
        }

        binding.cardViewFavorite.setOnClickListener {
            isFavorite = if (isFavorite) {
                binding.imageFavorite.setImageResource(com.example.impl.R.drawable.ic_favorite_unselected)
                false
            } else {
                binding.imageFavorite.setImageResource(com.example.impl.R.drawable.ic_favorite_selected)
                true
            }
        }

        binding.btnAddToBucket.setOnClickListener {
            sendOrder(orderList)
        }
    }

    private fun sendOrder(dish: MutableList<Dishes>) {
        orderListener.onOrderDataReceived(dish)
    }

    fun setOrderListener(listener: IOrderInfo) {
        orderListener = listener
    }

    override fun dismiss() {
        orderList.clear()
        super.dismiss()
    }
}