package com.splash.covid.tracker.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.splash.covid.tracker.repository.Repository
import com.splash.covid.tracker.repository.models.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.Exception

class RealTimeDataFragmentViewModel : ViewModel() {

    fun refreshData(context: Context)
    {
        try {
            Repository.getDistFromServer(context)
            Repository.getGraphDataFromServer(context)
        }
        catch (e : Exception)
        {
            e.printStackTrace()
        }
    }

    fun getDistDataFromCache(context: Context) :  MutableLiveData<Map<String,List<DistrictModel>>>
    {
        var liveData = MutableLiveData<Map<String,List<DistrictModel>>>()
        var distmap = HashMap<String,List<DistrictModel>>()

        Repository.getDistFromCache(context).observe(context as LifecycleOwner, Observer {
            try {
                if(it!=null)
                {
                   var dist = JSONObject(it.toString())


                    for(state in dist.keys())
                    {

                        var distList =ArrayList<DistrictModel>()

                        try {
                            var distJson  = JSONObject(dist[state].toString()).getJSONObject("districtData")

                            for(dist in distJson.keys())
                            {
                                var item = DistrictModel()
                                item.confirmed =  (distJson[dist] as JSONObject).get("confirmed").toString()
                                item.name = dist
                                item.lastupdatedtime =  (distJson[dist] as JSONObject).get("lastupdatedtime").toString()
                                item.delta = (distJson[dist] as JSONObject).getJSONObject("delta").get("confirmed").toString()
                                distList.add(item)
                            }
                            distmap.put(state,distList)
                        }
                        catch (e : Exception)
                        {
                            e.printStackTrace()
                        }
                    }



                   liveData.value = distmap
                }
            }
            catch (e : Exception)
            {
                e.printStackTrace()
            }
        })

        return liveData
    }

    fun getStateWiseFromCache(context: Context) : LiveData<List<StateModel>>
    {
        var liveData = MutableLiveData<List<StateModel>>()

        Repository.getResponseFromCache(context).observe(context as LifecycleOwner, Observer {
            try {
                if(it!=null)
                {
                    Log.d("Response",it.toString())

                    var state =  Gson().fromJson<List<StateModel>?>(it.toString(),object : TypeToken<List<StateModel>?>(){}.type)


                    liveData.value = state
                }
            }
            catch (e : Exception)
            {
                e.printStackTrace()
            }
        })

        return liveData
    }


    fun getTotalValuesFromCache(context: Context) : LiveData<StateModel>
    {
        var liveData = MutableLiveData<StateModel>()

        Repository.getResponseFromCache(context).observe(context as LifecycleOwner, Observer {
            try {
            if(it!=null)
            {

                Log.d("Response",it.getString(0))


                var total = Gson().fromJson<StateModel>(it[0].toString(),StateModel::class.java)
                liveData.value=total
                }
            }
            catch (e : Exception)
            {
                e.printStackTrace()
            }
        })

        return liveData
    }
}