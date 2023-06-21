package com.example.impl.presentation.fragments.categoryFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.api.ICategoryFragment
import com.example.api.IFragmentReplace
import com.example.impl.data.repository.LocationRepository
import com.example.impl.databinding.FragmentCategoryBinding
import com.example.impl.model.Categories
import com.example.impl.presentation.fragments.adapters.categoryAdapter.CategoryAdapter
import com.example.impl.presentation.fragments.dishesFragment.DishesFragment
import com.example.impl.utils.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class CategoryFragment : Fragment(), ICategoryFragment, KoinComponent {

    private val viewModel by viewModel<CategoryViewModel>()

    private var _binding: FragmentCategoryBinding? = null
    val binding get() = requireNotNull(_binding)

    private lateinit var locationRepository: LocationRepository

    private var fragmentChangeListener: IFragmentReplace? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.tvDate.text = getCurrentDate()

        locationRepository = LocationRepository(requireActivity())
        locationRepository.getLastLocation()

        initObserver()
        initUi()

        arguments?.let {
            CoroutineScope(Job()).launch {
                viewModel.getCategory()
            }
        }
    }

    private fun setAdapter(
        categoriesList: List<Categories>,
        recyclerView: RecyclerView,
    ) {
        val categoryAdapter = CategoryAdapter(categoriesList) {
            onCategoryClickListener(it, categoriesList[it-1].name)
        }
        recyclerView.adapter = categoryAdapter
        categoryAdapter.notifyDataSetChanged()
    }

    private fun onCategoryClickListener(position: Int, category: String) {
        val fragment = DishesFragment()

        fragment.arguments = Bundle().apply {
            putInt(DishesFragment.BUNDLE, position)
            putString(DishesFragment.BUNDLE, category)
        }

        fragmentChangeListener?.replaceFragment(fragment)
    }

    private fun initObserver() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            setAdapter(categories, binding.rvCategory)
        }

        locationRepository.currentLocation.observe(viewLifecycleOwner){
            binding.toolbar.tvLocation.text = it
        }
    }

    private fun initUi() {
        binding.rvCategory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is IFragmentReplace) {
            fragmentChangeListener = context
        } else {
            throw RuntimeException("$context")
        }
    }

    override fun onResume() {
        locationRepository.getLastLocation()
        super.onResume()
    }

    companion object {

        fun build() = CategoryFragment().apply {
            arguments = bundleOf()
        }
    }
}