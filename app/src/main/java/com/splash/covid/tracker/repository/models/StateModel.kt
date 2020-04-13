package com.splash.covid.tracker.repository.models

import com.google.gson.annotations.SerializedName

data class StateModel(

	@field:SerializedName("statenotes")
	var statenotes: String? = null,

	@field:SerializedName("recovered")
	var recovered: String? = null,

	@field:SerializedName("deltadeaths")
	var deltadeaths: String? = null,

	@field:SerializedName("deltarecovered")
	var deltarecovered: String? = null,

	@field:SerializedName("active")
	var active: String? = null,

	@field:SerializedName("deltaconfirmed")
	var deltaconfirmed: String? = null,

	@field:SerializedName("state")
	var state: String? = null,

	@field:SerializedName("statecode")
	var statecode: String? = null,

	@field:SerializedName("confirmed")
	var confirmed: String? = null,

	@field:SerializedName("deaths")
	var deaths: String? = null,

	@field:SerializedName("lastupdatedtime")
	var lastupdatedtime: String? = null,
    
    var visible : Boolean = false
)