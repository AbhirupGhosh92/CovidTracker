package com.splash.covid.tracker.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.splash.covid.tracker.constants.Constants

@Entity(tableName = "covid_graph")
data class GraphEntity(
    @PrimaryKey var id : String = Constants.GRAPH,
    @ColumnInfo(name = "response")var response: String
)