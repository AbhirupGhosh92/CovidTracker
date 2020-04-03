package com.splash.covid.tracker.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.splash.covid.tracker.constants.Constants


@Entity(tableName = "covid_response")
data class ResponseEntity(
    @PrimaryKey var id : String = Constants.RESPONNSE,
    @ColumnInfo(name = "response")var response: String
)