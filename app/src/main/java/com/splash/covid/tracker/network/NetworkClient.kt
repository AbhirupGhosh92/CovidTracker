package com.splash.covid.tracker.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.splash.covid.tracker.constants.Constants
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


object NetworkClient : CoroutineScope by MainScope() {
    var client = OkHttpClient()

    fun getDistrictData() : LiveData<String>
    {
        var resp = MutableLiveData<String>()

        var request: Request = Request.Builder()
            .url(Constants.url)
            .get()
            .build()

        Log.d("Request", Constants.url)

        launch(Dispatchers.IO) {
            try {
                var response: Response = client.newCall(request).execute()
                var result = response.body()?.string()

                if (response.isSuccessful) {
                    resp.postValue(
                        result
                    )
                }
            }
            catch (e : Exception)
            {
                e.printStackTrace()
            }
        }

        return resp
    }

    fun getGraphData() : LiveData<String>
    {
        var resp = MutableLiveData<String>()

        var request: Request = Request.Builder()
            .url(Constants.graphUrl)
            .get()
            .build()

        Log.d("Request", Constants.url)

        launch(Dispatchers.IO) {
            var response: Response = client.newCall(request).execute()
            var result = response.body()?.string()

            if(response.isSuccessful) {
                resp.postValue(
                    result
                )
            }
        }

        return resp
    }

}