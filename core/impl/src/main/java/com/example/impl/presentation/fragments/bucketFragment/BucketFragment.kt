package com.example.impl.presentation.fragments.bucketFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.data.repository.LocationRepository
import com.example.impl.databinding.FragmentBucketBinding
import com.example.impl.model.Dishes
import com.example.impl.model.Menu
import com.example.impl.presentation.dialog.CustomDialog.Companion.SHARED_BUNDLE
import com.example.impl.presentation.fragments.adapters.bucketAdapter.BucketAdapter
import com.example.impl.utils.getCurrentDate
import com.example.impl.utils.getParcelable
import org.koin.core.component.KoinComponent


class BucketFragment : Fragment(), KoinComponent {

    private var _binding: FragmentBucketBinding? = null
    val binding get() = requireNotNull(_binding)

    private lateinit var locationRepository: LocationRepository

    private var dishList: MutableMap<String, Int> = mutableMapOf()
    private var totalCost = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBucketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.tvDate.text = getCurrentDate()

        locationRepository = LocationRepository(requireActivity())
        locationRepository.getLastLocation()

        initUi()
        initObserver()
        getOrderData()
        calculateTotalCost()
    }

    private fun setAdapter(
        orderList: List<Dishes>,
        recyclerView: RecyclerView
    ) {
        val bucketAdapter = BucketAdapter(
            orderList,
            { dish, price ->
                dishList[dish] = price
                calculateTotalCost()
            },
            {
                dishList.remove(it)
                (recyclerView.adapter as? BucketAdapter)?.removeItem(it)
            }
        )
        recyclerView.adapter = bucketAdapter
        bucketAdapter.notifyDataSetChanged()
    }

    private fun calculateTotalCost() {
        totalCost = 0
        dishList.forEach {
            totalCost += it.value
        }
        binding.btnPay.text = "Оплатить $totalCost ₽"
    }

    private fun getOrderData() {
        val orderPrefs = context?.getSharedPreferences("orderDetails", Context.MODE_PRIVATE)
        orderPrefs?.getParcelable(SHARED_BUNDLE, Menu(arrayListOf()))
            ?.let { setAdapter(it.dishes, binding.rvBucket) }
    }

    private fun initUi() {
        binding.rvBucket.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun initObserver() {
        locationRepository.currentLocation.observe(viewLifecycleOwner) {
            binding.toolbar.tvLocation.text = it
        }
    }
}