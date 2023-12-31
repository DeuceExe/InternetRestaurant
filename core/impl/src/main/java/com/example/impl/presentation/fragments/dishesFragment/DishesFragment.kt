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
import com.example.uikit.R
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

        setToolbar()
        initObserver()
        initUi()

        arguments?.let {
            CoroutineScope(Job()).launch {
                viewModel.getDishes()
                binding.toolbar.tvCategory.text = it.getString(BUNDLE)
            }
        }
    }

    private fun setAdapter(
        dishesList: List<Dishes>
    ) {
        val tagsAdapter = TagAdapter(TAGS) { tags ->
            val filter = dishesList.filter { dish ->
                dish.tegs.any { tag ->
                    tag in tags
                }
            }
            setDishAdapter(filter, binding.rvDishes)
        }

        binding.rvTags.adapter = tagsAdapter
        setDishAdapter(dishesList, binding.rvDishes)
        tagsAdapter.notifyDataSetChanged()
    }

    private fun setDishAdapter(dishesList: List<Dishes>, recyclerView: RecyclerView) {
        val dishesAdapter = DishesAdapter(dishesList) {
            customDialog = CustomDialog(context!!)
            customDialog.show()
            customDialog.setDishInfo(dishesList[it - 1])
        }
        recyclerView.adapter = dishesAdapter
        dishesAdapter.notifyDataSetChanged()
    }

    private fun initObserver() {
        viewModel.dishes.observe(viewLifecycleOwner) { dishes ->
            setAdapter(dishes)
        }
    }

    private fun initUi() {
        binding.rvDishes.layoutManager = GridLayoutManager(context, 3)
        binding.rvTags.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setToolbar() {
        binding.toolbar.imageLocation.setImageResource(R.drawable.ic_back)
        binding.toolbar.imageLocation.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {

        val TAGS = listOf("Все меню", "Салаты", "С рисом", "С рыбой")
        const val BUNDLE = "bundle"
    }
}