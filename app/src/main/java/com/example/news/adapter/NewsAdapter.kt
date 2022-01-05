package com.example.news.adapter

import android.widget.TextView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.news.R
import com.example.news.database.NewsData

class NewsAdapter(@LayoutRes layoutResId: Int, date: MutableList<NewsData>? = null) :
    BaseQuickAdapter<NewsData, BaseViewHolder>(layoutResId, date) {

    override fun convert(holder: BaseViewHolder, item: NewsData) {
        val content = holder.getView<TextView>(R.id.contextTv)

        Glide.with(context)
            .load(item.urlToImage)
            .into(holder.getView(R.id.typeIv))
        content.text = item.title
    }
}