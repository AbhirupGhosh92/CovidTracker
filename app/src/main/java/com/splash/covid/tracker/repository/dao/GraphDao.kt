package com.splash.covid.tracker.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.splash.covid.tracker.constants.Constants
import com.splash.covid.tracker.repository.entity.GraphEntity

@Dao
interface GraphDao {

    @Query("select response from covid_graph where id = :id")
    fun getGraphObject( id : String = Constants.GRAPH): LiveData<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGraphEntity(vararg response: GraphEntity)

    @Delete
    fun deleteGraph(contact: GraphEntity)


    @Query("DELETE FROM covid_response")
    fun clearAll()
}