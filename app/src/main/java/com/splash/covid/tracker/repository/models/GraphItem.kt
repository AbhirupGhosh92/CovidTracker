package com.splash.covid.tracker.repository.models

import com.google.gson.annotations.SerializedName

data class GraphItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("dailyrecovered")
	val dailyrecovered: String? = null,

	@field:SerializedName("totalconfirmed")
	val totalconfirmed: String? = null,

	@field:SerializedName("totaldeceased")
	val totaldeceased: String? = null,

	@field:SerializedName("dailydeceased")
	val dailydeceased: String? = null,

	@field:SerializedName("totalrecovered")
	val totalrecovered: String? = null,

	@field:SerializedName("dailyconfirmed")
	val dailyconfirmed: String? = null
)