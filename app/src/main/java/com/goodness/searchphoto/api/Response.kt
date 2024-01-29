package com.goodness.searchphoto.api

import com.goodness.searchphoto.dto.PhotoData

data class PhotoResponse(
	val meta: Meta,
	val documents: List<PhotoData>
)

data class Meta(
	val total_count: Long,
	val pageable_count: Long,
	val is_end: Boolean
)
