package com.example.android.perludilindungi.bookmark

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.android.perludilindungi.databinding.FragmentBookmarkBinding
import com.example.android.perludilindungi.faskes.FaskesAdapter
import com.example.android.perludilindungi.faskes.FaskesViewModel
import com.example.android.perludilindungi.network.Faskes
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class BookmarkFragment : Fragment() {

    private val viewModel: FaskesViewModel by lazy {
        ViewModelProvider(this).get(FaskesViewModel::class.java)
    }

    private var _binding : FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    lateinit var dbBookmarkHandler: DbBookmarkHandler
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.faskesViewModel = viewModel
        dbBookmarkHandler = DbBookmarkHandler.getInstance(this@BookmarkFragment.context)

        dbBookmarkHandler.bookmarkDao().liveGetAllBookmark().observe(viewLifecycleOwner ){
            if(it==null || it.isEmpty()){
                binding.noBookmarkTextView.visibility = View.VISIBLE
            }else{
                binding.noBookmarkTextView.visibility = View.INVISIBLE
                val bookmarks = ArrayList<Faskes>()
                for(i in 0 until it.size){
                    val faskes: Faskes = Gson().fromJson(it[i].faskesJson, Faskes::class.java)
                    bookmarks.add(faskes)
                }
                binding.faskesList.adapter = FaskesAdapter(bookmarks, true)
            }
        }


        return binding.root
    }

}