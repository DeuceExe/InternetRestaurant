package com.example.impl.presentation.fragments.bucketFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.impl.databinding.FragmentBucketBinding
import org.koin.core.component.KoinComponent

class BucketFragment : Fragment(), KoinComponent {

    private var _binding: FragmentBucketBinding? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBucketBinding.inflate(inflater, container, false)
        return binding.root
    }
}