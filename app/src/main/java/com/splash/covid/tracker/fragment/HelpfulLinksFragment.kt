package com.splash.covid.tracker.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.splash.covid.tracker.R
import com.splash.covid.tracker.adapter.HelpfulLinksAdapter
import com.splash.covid.tracker.databinding.HelpfulLinksFragmentBinding
import com.splash.covid.tracker.repository.models.QuestionModel
import java.lang.Exception

class HelpfulLinksFragment : Fragment() {

    private lateinit var dataBinding : HelpfulLinksFragmentBinding
    private var questioList = ArrayList<QuestionModel>()
    private lateinit var currentContext : Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?

    {
        currentContext = requireContext()
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.helpful_links_fragment,container,false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        var file = ""

            file = resources.assets.open("links.json").bufferedReader().use{
                it.readText()
            }


        questioList = Gson().fromJson(file,object : TypeToken<ArrayList<QuestionModel>>(){}.type)

        dataBinding.rvUsefulLinnks.adapter = HelpfulLinksAdapter(questioList){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(it)
            try {
                activity?.startActivity(i)
            }
            catch (e : Exception)
            {
                e.printStackTrace()

            }
        }
        dataBinding.rvUsefulLinnks.layoutManager = LinearLayoutManager(currentContext)
        dataBinding.rvUsefulLinnks.itemAnimator = DefaultItemAnimator()
        dataBinding.rvUsefulLinnks.adapter?.notifyDataSetChanged()

    }


}