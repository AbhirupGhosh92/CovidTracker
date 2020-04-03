package com.splash.covid.tracker.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.splash.covid.tracker.constants.Constants
import com.splash.covid.tracker.repository.entity.ResponseEntity


@Dao
interface ResponseDao {

    @Query("select response from covid_response where id = :id")
    fun getResponseObject( id : String = Constants.RESPONNSE): LiveData<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResponseEntity(vararg response: ResponseEntity)

    @Delete
    fun deleteContact(contact: ResponseEntity)


    @Query("DELETE FROM covid_response")
    fun clearAll()

}