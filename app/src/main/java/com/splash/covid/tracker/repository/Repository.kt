package com.splash.covid.tracker.repository

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.splash.covid.tracker.network.NetworkClient
import com.splash.covid.tracker.repository.entity.GraphEntity
import com.splash.covid.tracker.repository.entity.ResponseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

object Repository {

    fun getResponseFromCache(context : Context) : LiveData<JSONObject>
    {
        var liveData = MutableLiveData<JSONObject>()

        AppDatabase.getInstance(context).responseDao().getResponseObject().observe(context as LifecycleOwner,
            Observer {
                if(it.isNullOrEmpty().not())
                liveData.value = JSONObject(it)
            })
        return  liveData
    }

    fun getDataFromServer(context: Context)
    {
        NetworkClient.getDashboardData().observe(context as LifecycleOwner, Observer {
            GlobalScope.launch(Dispatchers.IO) {
                AppDatabase.getInstance(context).responseDao()
                    .insertResponseEntity(ResponseEntity(response = it))
            }
        })
    }

    fun getGraphDataFromServer(context: Context)
    {
        NetworkClient.getGraphData().observe(context as LifecycleOwner, Observer {
            GlobalScope.launch(Dispatchers.IO) {
                AppDatabase.getInstance(context).graphDao()
                    .insertGraphEntity(GraphEntity(response = it))
            }
        })
    }

    fun getGraphFromCache(context : Context) : LiveData<JSONObject>
    {
        var liveData = MutableLiveData<JSONObject>()

        AppDatabase.getInstance(context).graphDao().getGraphObject().observe(context as LifecycleOwner,
            Observer {
                if(it.isNullOrEmpty().not())
                    liveData.value = JSONObject(it)
            })
        return  liveData
    }

}