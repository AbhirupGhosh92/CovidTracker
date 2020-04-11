package com.splash.covid.tracker.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.splash.covid.tracker.constants.Constants
import com.splash.covid.tracker.repository.entity.DistrictEntity
import com.splash.covid.tracker.repository.entity.GraphEntity

@Dao
interface DistDao {

    @Query("select response from covid_dist where id = :id")
    fun getDistObject( id : String = Constants.GRAPH): LiveData<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDistEntity(vararg response: DistrictEntity)

    @Delete
    fun deleteDist(contact: DistrictEntity)


    @Query("DELETE FROM covid_dist")
    fun clearAll()
}