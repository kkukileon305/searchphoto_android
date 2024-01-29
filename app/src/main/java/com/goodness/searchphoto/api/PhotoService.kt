package com.goodness.searchphoto.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PhotoService {
	@GET("v2/search/image")
	suspend fun getDataListByKeyword(
		@Header("Authorization") authorization: String,
		@Query("query") query: String,
		@Query("page") page: Int = 1,
		@Query("size") size: Int = 80,
		@Query("sort") sort: String = "accuracy",
	): Response<PhotoResponse>
}