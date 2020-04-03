package com.splash.covid.tracker.repository.models

data class StateModel(
    var active:String,
    var confirmed:String,
    var deaths:String,
    var delta:DeltaModel?,
    var lastupdatedtime:String,
    var recovered:String,
    var state:String,
    var district:List<DistrictModel>?,
    var visible : Boolean = false
)