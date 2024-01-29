package com.goodness.searchphoto

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goodness.searchphoto.api.PhotoApiInstance
import com.goodness.searchphoto.dto.PhotoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotosViewModel : ViewModel() {
	private var _photos: MutableLiveData<List<PhotoData>> = MutableLiveData(emptyList())
	private var _focusedPhotos: MutableLiveData<List<PhotoData>> = MutableLiveData(emptyList())
	private var _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
	private var _isPhotosEmpty: MutableLiveData<Boolean> = MutableLiveData(false)

	val photos: LiveData<List<PhotoData>>
		get() = _photos
	val focusedPhotos: LiveData<List<PhotoData>>
		get() = _focusedPhotos
	val isLoading: LiveData<Boolean>
		get() = _isLoading
	val isPhotosEmpty: LiveData<Boolean>
		get() = _isPhotosEmpty

	private val photoService = PhotoApiInstance.getService()

	fun fetchPhotosByKeyword(keyword: String) {
		_isLoading.value = true
		_photos.value = emptyList()
		_focusedPhotos.value = emptyList()

		CoroutineScope(Dispatchers.IO).launch {
			val response = photoService.getDataListByKeyword(
				authorization = "KakaoAK eb940909a8aa52b32d52415b0e1fcffd",
				query = keyword,
				sort = "recency"
			)

			if (response.isSuccessful) {
				val newPhotoList = response.body()?.documents ?: emptyList()

				withContext(Dispatchers.Main) {
					_photos.value = newPhotoList
					_isPhotosEmpty.value = newPhotoList.isEmpty()
				}
			} else {
				Log.d("debug", "응답 에러")
			}

			withContext(Dispatchers.Main) {
				_isLoading.value = false
			}
		}
	}

	fun updateFocusedPhotos(newPhotoData: PhotoData) {
		val currentList = _focusedPhotos.value ?: emptyList()

		_focusedPhotos.value = if (currentList.any { it.thumbnail_url == newPhotoData.thumbnail_url }) {
			currentList.filter { it.thumbnail_url != newPhotoData.thumbnail_url }
		} else {
			currentList.plus(newPhotoData)
		}
	}
}