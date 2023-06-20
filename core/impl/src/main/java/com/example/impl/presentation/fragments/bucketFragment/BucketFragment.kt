package com.example.impl.presentation.fragments.bucketFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.FragmentBucketBinding
import com.example.impl.model.Dishes
import com.example.impl.presentation.fragments.adapters.bucketAdapter.BucketAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class BucketFragment : Fragment(), KoinComponent {

    private val viewModel by viewModel<BucketViewModel>()

    private var _binding: FragmentBucketBinding? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBucketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initUi()
    }

    private fun setAdapter(
        orderList: List<Dishes>,
        recyclerView: RecyclerView,
    ) {
        val bucketAdapter = BucketAdapter(orderList)
        recyclerView.adapter = bucketAdapter
        bucketAdapter.notifyDataSetChanged()
    }

    private fun initObserver() {
        viewModel.orderList.observe(viewLifecycleOwner) { orderList ->
            setAdapter(orderList, binding.rvBucket)
        }
    }

    private fun initUi() {
        binding.rvBucket.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}