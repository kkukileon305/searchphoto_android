package com.goodness.searchphoto.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.goodness.searchphoto.PhotosViewModel
import com.goodness.searchphoto.adapters.SearchListRecyclerViewAdapter
import com.goodness.searchphoto.databinding.FragmentSearchListBinding

class SearchListFragment : Fragment() {
	private lateinit var binding: FragmentSearchListBinding

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

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSearchListBinding.inflate(inflater)

		initRecyclerView()
		initViewModel()
		return binding.root
	}

	private fun initRecyclerView() {
		val recyclerView = binding.rvSearchList
		recyclerView.adapter = adapter
		recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
	}

	private fun initViewModel() {
		photosViewModel.photos.observe(viewLifecycleOwner) { newPhotoList ->
			newPhotoList?.let { adapter.updateDataList(it) }
		}

		photosViewModel.focusedPhotos.observe(viewLifecycleOwner) { newFocusedPhotoList ->
			newFocusedPhotoList?.let { adapter.updateFocusedDataList(it) }
		}

		photosViewModel.isPhotosEmpty.observe(viewLifecycleOwner) { isPhotosEmpty ->
			binding.tvIsEnd.visibility = if (isPhotosEmpty) View.VISIBLE else View.GONE
		}

		photosViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
			with(binding.sflSearchList) {
				if (isLoading) {
					startShimmer()
					visibility = View.VISIBLE
				} else {
					stopShimmer()
					visibility = View.GONE
				}
			}
		}
	}
}