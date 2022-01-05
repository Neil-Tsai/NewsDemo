package com.example.news

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.adapter.NewsAdapter
import com.example.news.databinding.ActivityMainBinding
import com.example.news.viewmodel.MainViewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupView()
        setupObserve()
        viewModel.getNews()
    }

    private fun setupView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(context, R.drawable.list_divider)!!)
        binding.recyclerView.addItemDecoration(itemDecorator)
        binding.recyclerView.adapter = NewsAdapter(R.layout.item_news)
    }

    private fun setupObserve() {

        viewModel.newsListData.observe(this, {
            (binding.recyclerView.adapter as NewsAdapter).setList(it)
        })
    }
}