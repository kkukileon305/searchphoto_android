package com.goodness.searchphoto.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.goodness.searchphoto.PhotosViewModel
import com.goodness.searchphoto.adapters.SearchListRecyclerViewAdapter
import com.goodness.searchphoto.databinding.FragmentLikesBinding

class LikesFragment : Fragment() {
	private lateinit var binding: FragmentLikesBinding

	// ViewModelProviderのownerを注意！
	private val photosViewModel by lazy { ViewModelProvider(requireActivity())[PhotosViewModel::class.java] }
	private val adapter by lazy {
		SearchListRecyclerViewAdapter(
			emptyList(),
			emptyList(),
			requireActivity(),
			photosViewModel
		)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentLikesBinding.inflate(inflater)

		initRecyclerView()
		initViewModel()
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}

	private fun initRecyclerView() {
		val recyclerView = binding.rvSearchList
		recyclerView.adapter = adapter
		recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
	}

	private fun initViewModel() {
		photosViewModel.focusedPhotos.observe(viewLifecycleOwner) { photos ->
			photos?.let {
				adapter.updateDataList(it)
				adapter.updateFocusedDataList(it)
			}
		}
	}
}