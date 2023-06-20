package com.example.impl.presentation.fragments.dishesFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.FragmentDishesBinding
import com.example.impl.model.Dishes
import com.example.impl.presentation.dialog.CustomDialog
import com.example.impl.presentation.fragments.adapters.dishesAdapter.DishesAdapter
import com.example.impl.presentation.fragments.adapters.tagAdapter.TagAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class DishesFragment : Fragment(), KoinComponent {

    private val viewModel by viewModel<DishesViewModel>()

    private var _binding: FragmentDishesBinding? = null
    val binding get() = requireNotNull(_binding)

    private lateinit var customDialog: CustomDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initUi()

        arguments?.let {
            CoroutineScope(Job()).launch {
                viewModel.getDishes()
            }
        }
    }

    private fun setAdapter(
        dishesList: List<Dishes>?,
        tagList: List<String>?,
        recyclerView: RecyclerView
    ) {
        if (dishesList != null) {
            val dishesAdapter = DishesAdapter(dishesList) {
                customDialog = CustomDialog(context!!)
                customDialog.show()
                customDialog.setDishInfo(dishesList[it-1])
            }

            recyclerView.adapter = dishesAdapter
            dishesAdapter.notifyDataSetChanged()
        } else if (tagList != null) {
            val tagsAdapter = TagAdapter(tagList)
            recyclerView.adapter = tagsAdapter
            tagsAdapter.notifyDataSetChanged()
        }
    }

    private fun initObserver() {
        viewModel.dishes.observe(viewLifecycleOwner) { dishes ->
            setAdapter(dishes, null, binding.rvDishes)
        }
        setAdapter(null, TAGS, binding.rvTags)
    }

    private fun initUi() {
        binding.rvDishes.layoutManager = GridLayoutManager(context, 3)
        binding.rvTags.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    companion object {

        val TAGS = listOf("Все меню", "Салаты", "С рисом", "С рыбой")
        const val BUNDLE = "bundle"
    }
}