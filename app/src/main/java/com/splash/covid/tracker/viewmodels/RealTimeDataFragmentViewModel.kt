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

    fun getDistDataFromCache(context: Context) :  MutableLiveData<List<DistrictModel>>
    {
        var liveData = MutableLiveData<List<DistrictModel>>()

        Repository.getDistFromCache(context).observe(context as LifecycleOwner, Observer {
            try {
                if(it!=null)
                {
                    Log.d("Response",it.toString())

                //    var state =  Gson().fromJson<List<DistrictModel>?>(it.toString(),object : TypeToken<List<StateModel>?>(){}.type)


                    liveData.value = null
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

    private fun stateParser(jsonObject: JSONObject) : StateModel
    {
        var delta = DeltaModel()
        var district = districtParser(jsonObject)

        var active : String = "0"
        var confirmed : String = "0"
        var deaths : String = "0"
        var lastupdatedtime : String = ""
        var recovered : String = "0"
        var state : String = "UNKNOWN"

        if(jsonObject.has("active"))
            active =  jsonObject.getString("active")

        if(jsonObject.has("confirmed"))
           confirmed =  jsonObject.getString("confirmed")

        if(jsonObject.has("deaths"))
           deaths =  jsonObject.getString("deaths")

        if(jsonObject.has("lastupdatedtime"))
            lastupdatedtime = jsonObject.getString("lastupdatedtime")

        if(jsonObject.has("recovered"))
            recovered = jsonObject.getString("recovered")

        if(jsonObject.has("state"))
            state = jsonObject.getString("state")


        return StateModel(
           active,
           confirmed,
           deaths,
            delta,
            lastupdatedtime,
            recovered,
            state,
            district!!
        )
    }

    private fun districtParser(jsonObject: JSONObject) : List<DistrictModel>?
    {
        var districtModel : ArrayList<DistrictModel>? = ArrayList()
        if(jsonObject.has("districtData"))
        {
            var dist = jsonObject.getJSONObject("districtData")

            dist.keys().forEach {


                districtModel?.add(DistrictModel(
                    it,
                    dist.getJSONObject(it).getString("confirmed"),
                    dist.getJSONObject(it).getString("lastupdatedtime"),
                    dist.getJSONObject(it).getJSONObject("delta").getString("confirmed")
                ))
            }
        }

        return districtModel
    }
}