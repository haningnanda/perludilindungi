package com.example.android.perludilindungi.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.android.perludilindungi.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by lazy {
        ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = FragmentNewsBinding.inflate(inflater, container, false)

        // bind viewModel ke variable di XML
        binding.lifecycleOwner = this
        binding.newsViewModel = viewModel

        // bind adapter RecyclerView
        viewModel.news.observe(viewLifecycleOwner) { news ->
            val adapter = NewsAdapter(news)
            binding.newsList.adapter = adapter
        }

        return binding.root
    }
}