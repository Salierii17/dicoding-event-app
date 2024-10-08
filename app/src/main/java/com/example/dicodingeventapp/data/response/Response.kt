package com.example.dicodingeventapp.data.response

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("restaurants")
	val restaurants: List<RestaurantsItem>
)

data class RestaurantsItem(

	@field:SerializedName("pictureId")
	val pictureId: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String
)
