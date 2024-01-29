package com.goodness.searchphoto.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goodness.searchphoto.PhotosViewModel
import com.goodness.searchphoto.databinding.PhotoItemBinding
import com.goodness.searchphoto.dto.PhotoData
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class SearchListRecyclerViewAdapter(
	private var photos: List<PhotoData>,
	private var focusedPhotos: List<PhotoData>,
	private val context: Context,
	private val photosViewModel: PhotosViewModel
) : RecyclerView.Adapter<SearchListRecyclerViewAdapter.ViewHolder>() {
	inner class ViewHolder(binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {
		val parent = binding.root
		val imageView = binding.ivPhoto
		val photoTitle = binding.tvPhotoTitle
		val date = binding.tvDate
		val ivFocused = binding.ivFocused
	}

	override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
		val binding = PhotoItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
		val data = photos[position]

		with(viewHolder) {

			Glide.with(context).load(data.thumbnail_url).into(imageView)

			date.text = formatDate(data.datetime)
			photoTitle.text = data.display_sitename

			val isFocused = focusedPhotos.any { it.thumbnail_url == data.thumbnail_url }
			ivFocused.visibility = if (isFocused) View.VISIBLE else View.GONE

			parent.setOnClickListener {
				photosViewModel.updateFocusedPhotos(data)
			}
		}
	}

	override fun getItemCount() = photos.size

	fun updateDataList(newDataList: List<PhotoData>) {
		photos = newDataList
		notifyDataSetChanged()
	}

	fun updateFocusedDataList(newFocusedPhotos: List<PhotoData>) {
		focusedPhotos = newFocusedPhotos
		notifyDataSetChanged()
	}

	private fun formatDate(date: String): String {
		val originalDateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
		val targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
		return originalDateTime.format(targetFormatter)
	}
}
