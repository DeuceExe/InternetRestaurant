package com.example.impl.presentation.fragments.categoryFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.api.ICategoryFragment
import com.example.impl.databinding.FragmentMainBinding
import org.koin.core.component.KoinComponent

class CategoryFragment : Fragment(), ICategoryFragment, KoinComponent {

    private var _binding: FragmentMainBinding? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        fun build() = CategoryFragment().apply {
            arguments = bundleOf()
        }
    }
}