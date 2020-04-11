package com.splash.covid.tracker.repository.models

data class DeltaModel(
    var active : Int = 0,
    var confirmed:Int = 0,
    var deaths:Int = 0,
    var recovered:Int = 0
)