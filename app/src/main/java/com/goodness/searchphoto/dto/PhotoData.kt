package com.goodness.searchphoto.dto

data class PhotoData(
	val collection: String,
	val thumbnail_url: String,
	val image_url: String,
	val width: Long,
	val height: Long,
	val display_sitename: String,
	val doc_url: String,
	val datetime: String,
)