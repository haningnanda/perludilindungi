package com.example.android.perludilindungi.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.android.perludilindungi.R
import com.example.android.perludilindungi.bindImage
import com.example.android.perludilindungi.dateNewsFormatter
import com.example.android.perludilindungi.network.News


class NewsAdapter(private val data: List<News>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbnailBerita: ImageView = itemView.findViewById(R.id.thumbnail_berita)
        var judulBerita: TextView = itemView.findViewById(R.id.judul_berita)
        var tanggalBerita: TextView = itemView.findViewById(R.id.tanggal_berita)
        var linkUrlNews: String = ""

        init {
            itemView.setOnClickListener { view: View ->
                view.findNavController().navigate(
                    NewsFragmentDirections.actionNewsFragmentToWebViewNewsFragment(linkUrlNews)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_berita, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = data[position]

        holder.judulBerita.text = item.title
        holder.tanggalBerita.text = dateNewsFormatter(item.pubDate)
        bindImage(holder.thumbnailBerita, item.enclosure._url)
        holder.linkUrlNews = item.link[0]
    }

    override fun getItemCount(): Int {
        return data.size
    }
}