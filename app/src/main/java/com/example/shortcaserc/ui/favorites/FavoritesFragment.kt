package com.example.shortcaserc.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shortcaserc.database.ComicObject
import com.example.shortcaserc.databinding.FragmentFavoritesBinding


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    private val binding get() = _binding!!

    private lateinit var favoritesViewModel: FavoritesViewModel

    private lateinit var favoritesAdapter: FavoritesAdapter //by lazy { FavoritesAdapter() }

    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var deleteAllFavoritesButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoritesViewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        favoritesRecyclerView = binding.favoritesRecyclerView
        deleteAllFavoritesButton = binding.deleteAllFavoritesButton

        favoritesAdapter = FavoritesAdapter(emptyList())
        favoritesRecyclerView.adapter = favoritesAdapter
        favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesViewModel.readComic.observe(requireActivity()) {
            favoritesAdapter.updateData(it)
        }

        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListeners() {
        deleteAllFavoritesButton.setOnClickListener {
            favoritesViewModel.deleteAllFavorites()
        }
    }
}