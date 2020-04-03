package com.splash.covid.tracker.repository.models

data class TotalValuesModel(
    var active:String,
    var confirmed:String,
    var deaths:String,
    var delta:DeltaModel,
    var lastupdatedtime:String,
    var recovered:String,
    var state:String
)