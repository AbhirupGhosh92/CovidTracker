package com.splash.covid.tracker.repository

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.splash.covid.tracker.network.NetworkClient
import com.splash.covid.tracker.repository.entity.DistrictEntity
import com.splash.covid.tracker.repository.entity.GraphEntity
import com.splash.covid.tracker.repository.entity.ResponseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

object Repository {

    fun getResponseFromCache(context : Context) : LiveData<JSONArray>
    {
        var liveData = MutableLiveData<JSONArray>()

        AppDatabase.getInstance(context).responseDao().getResponseObject().observe(context as LifecycleOwner,
            Observer {
                if(it.isNullOrEmpty().not())
                liveData.value = JSONArray(it)
            })
        return  liveData
    }


    fun getGraphDataFromServer(context: Context)
    {
        NetworkClient.getGraphData().observe(context as LifecycleOwner, Observer {
            GlobalScope.launch(Dispatchers.IO) {
                AppDatabase.getInstance(context).graphDao()
                    .insertGraphEntity(GraphEntity(response = JSONObject(it).getJSONArray("cases_time_series").toString()))
            }

            GlobalScope.launch(Dispatchers.IO) {
                AppDatabase.getInstance(context).responseDao()
                    .insertResponseEntity(ResponseEntity(response = JSONObject(it).getJSONArray("statewise").toString()))
            }
        })
    }

    fun getDistFromServer(context: Context)
    {
        NetworkClient.getDistrictData().observe(context as LifecycleOwner, Observer {
            GlobalScope.launch(Dispatchers.IO) {
                AppDatabase.getInstance(context).distDao()
                    .insertDistEntity(DistrictEntity(response = it))
            }

        })
    }

    fun getDistFromCache(context : Context) : LiveData<JSONObject>
    {
        var liveData = MutableLiveData<JSONObject>()

        AppDatabase.getInstance(context).distDao().getDistObject().observe(context as LifecycleOwner,
            Observer {
                if(it.isNullOrEmpty().not())
                    liveData.value = JSONObject(it)
            })
        return  liveData
    }


    fun getGraphFromCache(context : Context) : LiveData<JSONArray>
    {
        var liveData = MutableLiveData<JSONArray>()

        AppDatabase.getInstance(context).graphDao().getGraphObject().observe(context as LifecycleOwner,
            Observer {
                if(it.isNullOrEmpty().not())
                    liveData.value = JSONArray(it)
            })
        return  liveData
    }

}