package com.splash.covid.tracker.viewmodels

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.splash.covid.tracker.repository.Repository
import com.splash.covid.tracker.repository.models.GraphItem
import com.jjoe64.graphview.series.DataPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GraphDataFragmentViewModel : ViewModel() {



    fun getGraphDataFromCache(context: Context) : LiveData<List<GraphItem>>
    {
        var liveData = MutableLiveData<List<GraphItem>>()

        Repository.getGraphFromCache(context).observe(context as LifecycleOwner, Observer {
            if(it != null)
            {

                liveData.value = Gson().fromJson(it.toString(),object : TypeToken<List<GraphItem>>(){}.type)
            }
        })

        return liveData
    }

    fun formatData(graphList : List<GraphItem>, sippet : (dataArrayInfected : Array<DataPoint?>,
                                                          dataArrayRecovered : Array<DataPoint?>,
                                                          dataArrayDead : Array<DataPoint?>,
                                                          dataArrayInfectedDaily : Array<DataPoint?>,
                                                          dataArrayRecoveredDaily : Array<DataPoint?>,
                                                          dataArrayDeadDaily : Array<DataPoint?>
                                                          ) -> Unit)
    {
        var dataListInfected = ArrayList<DataPoint>()
        var dataListRecovered = ArrayList<DataPoint>()
        var dataListDead = ArrayList<DataPoint>()
        var dataListInfectedDaily = ArrayList<DataPoint>()
        var dataListRecoveredDaily = ArrayList<DataPoint>()
        var dataListDeadDaily = ArrayList<DataPoint>()

        graphList.forEach {
            var date = SimpleDateFormat("dd MMMM yyyy").parse("${it.date} ${Calendar.getInstance().get(
                Calendar.YEAR)}")

            dataListInfected.add(DataPoint(date,it.totalconfirmed?.toDouble()!!))
            dataListRecovered.add(DataPoint(date,it.totalrecovered?.toDouble()!!))
            dataListDead.add(DataPoint(date,it.totaldeceased?.toDouble()!!))

            dataListInfectedDaily.add(DataPoint(date,it.dailyconfirmed?.toDouble()!!))
            dataListRecoveredDaily.add(DataPoint(date,it.dailyrecovered?.toDouble()!!))
            dataListDeadDaily.add(DataPoint(date,it.dailydeceased?.toDouble()!!))

        }

        var dataArrayInfected = arrayOfNulls<DataPoint?>(dataListInfected.size)
        var dataArrayRecovered = arrayOfNulls<DataPoint?>(dataListRecovered.size)
        var dataArrayDead = arrayOfNulls<DataPoint?>(dataListDead.size)

        var dataArrayInfectedDaily = arrayOfNulls<DataPoint?>(dataListInfectedDaily.size)
        var dataArrayRecoveredDaily = arrayOfNulls<DataPoint?>(dataListRecoveredDaily.size)
        var dataArrayDeadDaily = arrayOfNulls<DataPoint?>(dataListDeadDaily.size)


        dataListInfected.toArray(dataArrayInfected)
        dataListRecovered.toArray(dataArrayRecovered)
        dataListDead.toArray(dataArrayDead)

        dataListInfectedDaily.toArray(dataArrayInfectedDaily)
        dataListRecoveredDaily.toArray(dataArrayRecoveredDaily)
        dataListDeadDaily.toArray(dataArrayDeadDaily)

        sippet.invoke(dataArrayInfected,dataArrayRecovered,dataArrayDead,dataArrayInfectedDaily,dataArrayRecoveredDaily,dataArrayDeadDaily)

    }



}