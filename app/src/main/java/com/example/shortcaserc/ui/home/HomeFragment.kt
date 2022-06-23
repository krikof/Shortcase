package com.example.shortcaserc.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.shortcaserc.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var mainViewModel: HomeViewModel

    private lateinit var previousButton: Button
    private lateinit var nextButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root

        previousButton = binding.previousButton
        nextButton = binding.nextButton

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListeners() {
        previousButton.setOnClickListener {
            mainViewModel.checkForInternetConnectivity { response ->
                if (response) {
                    mainViewModel.previousComic()
                }
            }
        }

        nextButton.setOnClickListener {
            mainViewModel.checkForInternetConnectivity { response ->
                if (response) {
                    mainViewModel.nextComic()
                }
            }
        }
    }
}