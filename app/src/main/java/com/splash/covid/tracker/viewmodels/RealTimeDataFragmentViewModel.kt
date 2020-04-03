package com.splash.covid.tracker.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.splash.covid.tracker.repository.Repository
import com.splash.covid.tracker.repository.models.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.Exception

class RealTimeDataFragmentViewModel : ViewModel() {

    fun refreshData(context: Context)
    {
        try {
            Repository.getDataFromServer(context)
            Repository.getGraphDataFromServer(context)
        }
        catch (e : Exception)
        {
            e.printStackTrace()
        }
    }

    fun getStateWiseFromCache(context: Context) : LiveData<List<StateModel>>
    {
        var liveData = MutableLiveData<List<StateModel>>()

        Repository.getResponseFromCache(context).observe(context as LifecycleOwner, Observer {
            try {
                if(it!=null)
                {
                    Log.d("Response",it.toString())

                    var stateList = ArrayList<StateModel>()
                    var state = it.getJSONObject("state_wise")
                    state.keys().forEach {
                        stateList.add(stateParser(state[it] as JSONObject))
                    }

                    liveData.value = stateList
                }
            }
            catch (e : Exception)
            {
                e.printStackTrace()
            }
        })

        return liveData
    }


    fun getKeyValuesFromCache(context: Context) : LiveData<KeyValuesModel>
    {
        var liveData = MutableLiveData<KeyValuesModel>()

        Repository.getResponseFromCache(context).observe(context as LifecycleOwner, Observer {
            if(it!=null && it.has("key_values"))
            {
                Log.d("Response",it.toString())

                var data = it.getJSONArray("key_values")

                try {
                    liveData.value =
                        KeyValuesModel(
                            (data[0] as JSONObject).get("confirmeddelta").toString(),
                            (data[0] as JSONObject).get("counterforautotimeupdate").toString(),
                            (data[0] as JSONObject).get("deceaseddelta").toString(),
                            (data[0] as JSONObject).get("lastupdatedtime").toString(),
                            (data[0] as JSONObject).get("recovereddelta").toString(),
                            (data[0] as JSONObject).get("statesdelta").toString()
                        )
                }
                catch (e : Exception)
                {
                    e.printStackTrace()
                }


            }
        })

        return liveData
    }

    fun getTotalValuesFromCache(context: Context) : LiveData<TotalValuesModel>
    {
        var liveData = MutableLiveData<TotalValuesModel>()

        Repository.getResponseFromCache(context).observe(context as LifecycleOwner, Observer {
            try {
            if(it!=null && it.has("total_values"))
            {

                Log.d("Response",it.toString())

                var data = it.getJSONObject("total_values")
                var delta = data.getJSONObject("delta")
                var deltaModel = DeltaModel(
                    (delta as JSONObject).get("active").toString(),
                    (delta as JSONObject).get("confirmed").toString(),
                    (delta as JSONObject).get("deaths").toString(),
                    (data as JSONObject).get("recovered").toString()
                )

                    liveData.value =
                        TotalValuesModel(
                            (data as JSONObject).get("active").toString(),
                            (data as JSONObject).get("confirmed").toString(),
                            (data as JSONObject).get("deaths").toString(),
                            deltaModel,
                            (data as JSONObject).get("lastupdatedtime").toString(),
                            (data as JSONObject).get("recovered").toString(),
                            (data as JSONObject).get("state").toString()
                        )
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
        var delta = deltaParser(jsonObject)
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
            district
        )
    }

    private fun districtParser(jsonObject: JSONObject) : List<DistrictModel>?
    {
        var districtModel : ArrayList<DistrictModel>? = ArrayList()
        if(jsonObject.has("district"))
        {
            var dist = jsonObject.getJSONObject("district")

            dist.keys().forEach {


                districtModel?.add(DistrictModel(
                    it,
                    dist.getJSONObject(it).getString("confirmed"),
                    dist.getJSONObject(it).getString("lastupdatedtime")
                ))
            }
        }

        return districtModel
    }

    private fun deltaParser(jsonObject: JSONObject) : DeltaModel?
    {
        var deltaModel : DeltaModel? = null
        if(jsonObject.has("delta"))
        {
            var delta = jsonObject.getJSONObject("delta")
            deltaModel = DeltaModel(
                delta.getString("active"),
                delta.getString("confirmed"),
                delta.getString("deaths"),
                delta.getString("recovered")
            )
        }

        return deltaModel
    }
}