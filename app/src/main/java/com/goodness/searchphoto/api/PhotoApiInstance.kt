package com.goodness.searchphoto.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PhotoApiInstance {
	val BASE_URL = "https://dapi.kakao.com/"

	val client = Retrofit
		.Builder()
		.baseUrl(BASE_URL)
		.addConverterFactory(GsonConverterFactory.create())
		.build()

	fun getService(): PhotoService {
		return client.create(PhotoService::class.java)
	}
}
