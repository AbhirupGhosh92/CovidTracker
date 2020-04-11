package com.splash.covid.tracker.repository.models

data class DistrictModel(
    var name: String = "",
    var confirmed:String = "",
    var lastupdatedtime :String = "",
    var delta : String = "",
    var visible : Boolean = false
)