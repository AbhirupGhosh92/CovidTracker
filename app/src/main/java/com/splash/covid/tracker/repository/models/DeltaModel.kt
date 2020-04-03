package com.splash.covid.tracker.repository.models

data class DeltaModel(
    var active : String,
    var confirmed:String,
    var deaths:String,
    var recovered:String
)