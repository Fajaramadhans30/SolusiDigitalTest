package com.test.solusidigitaltest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.solusidigitaltest.R
import com.test.solusidigitaltest.commons.Constant
import com.test.solusidigitaltest.model.Article
import kotlinx.android.synthetic.main.news_item.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(
    private var listDataNews: MutableList<Article?>,
    private val context: Context?,
    private val listener: (Article) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val v =
                LayoutInflater.from(context).inflate(R.layout.news_item, parent, false)
            Item(v)
        } else {
            val v =
                LayoutInflater.from(context).inflate(R.layout.progress_loading, parent, false)
            LoadingHolder(v)
        }
    }


    fun addData(listDataNews: MutableList<Article?>) {
        listDataNews.let { this.listDataNews.addAll(it) }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listDataNews.size//To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        context?.let {
            listDataNews[position]?.let { it1 ->
                (holder as Item).bindItem(
                    it1,
                    listener, context
                )
            }
        }
    }

    class Item(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bindItem(
            dataNews: Article,
            listener: (Article) -> Unit,
            context: Context?
        ) {

            itemView.title.text =
                dataNews.title
            itemView.subtitle.text =
                dataNews.author


            val requestOption = RequestOptions()
            requestOption.placeholder(R.drawable.ic_launcher_background)
            requestOption.error(R.drawable.ic_launcher_background)
            Glide.with(itemView.imageView).setDefaultRequestOptions(requestOption)
                .load(dataNews.urlToImage).into(itemView.imageView)

            itemView.setOnClickListener {
                listener(dataNews)
            }

        }
    }

    class LoadingHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}