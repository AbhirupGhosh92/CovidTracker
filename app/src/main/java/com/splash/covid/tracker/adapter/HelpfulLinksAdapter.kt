package com.splash.covid.tracker.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.splash.covid.tracker.R
import com.splash.covid.tracker.repository.models.QuestionModel
import com.splash.covid.tracker.viewholders.HelpfulLinksViewHolder
import java.lang.Exception


class HelpfulLinksAdapter(var questionList: List<QuestionModel> , var snippet : (url : String) -> Unit) : RecyclerView.Adapter<HelpfulLinksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpfulLinksViewHolder {
        return HelpfulLinksViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.helpful_links_item_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: HelpfulLinksViewHolder, position: Int) {
        holder.helpfulLinksItemBinding.question.text = questionList[position].question
        holder.helpfulLinksItemBinding.answer.text = questionList[position].answer

        if( questionList[position].browse)
        {

            holder.helpfulLinksItemBinding.answer.setOnClickListener {
                val url = questionList[position].answer
                try {
                    snippet.invoke(url)
                }
                catch (e : Exception)
                {
                    e.printStackTrace()

                }
            }

        }
    }
}