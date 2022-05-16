package com.example.android.perludilindungi.faskes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.android.perludilindungi.R
import com.example.android.perludilindungi.bindImage
import com.example.android.perludilindungi.bookmark.DbBookmarkHandler
import com.example.android.perludilindungi.databinding.FragmentDetailFaskesBinding
import com.example.android.perludilindungi.databinding.FragmentLokasiVaksinBinding
import java.lang.Exception

class DetailFaskesFragment : Fragment() {
    private var _binding : FragmentDetailFaskesBinding? = null
    private val binding get() = _binding!!
    lateinit var dbBookmarkHandler:DbBookmarkHandler
    private val viewModel: FaskesViewModel by lazy {
        ViewModelProvider(this).get(FaskesViewModel::class.java)
    }
    var bookmarked: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        // Inflate the layout for this fragment
        _binding = FragmentDetailFaskesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.faskesViewModel = viewModel

        val args = DetailFaskesFragmentArgs.fromBundle(requireArguments())
        dbBookmarkHandler = DbBookmarkHandler.getInstance(this@DetailFaskesFragment.context)
        viewModel.getFaskesDetail(args.prov,args.city,args.id)
        viewModel.detail.observe(viewLifecycleOwner){ faskes ->
            bookmarked = faskes.isBookmarked
            binding.judulFaskes.text = faskes.nama
            binding.kodeFaskes.text = "Kode: ".plus(faskes.kode)
            binding.alamatFaskes.text = faskes.alamat
            binding.teleponFaskes.text = "No Telp: ".plus(faskes.telp)
            binding.jenisFaskes.text = faskes.jenis_faskes
            binding.status.text = faskes.status
            if (faskes.status == "Siap Vaksinasi"){
                binding.imgCheck.setImageResource(R.drawable.cekin_woke)
            } else {
                binding.imgCheck.setImageResource(R.drawable.cekin_err)
            }
            binding.btnMaps.setOnClickListener {
                val geo = "geo:"

                val gmmIntentUri = Uri.parse(geo.plus(faskes.latitude).plus(",").plus(faskes.longitude))
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }

            if(faskes.isBookmarked || bookmarked){
                binding.btnBookmark.text = "- UnBookmark"
            }else{
                binding.btnBookmark.text = "+ Bookmark"
            }
            binding.btnBookmark.setOnClickListener {
                if(faskes.isBookmarked || bookmarked){
                    try {
                        viewModel.deleteBookmark(faskes, this@DetailFaskesFragment.context)
                        Toast.makeText(this@DetailFaskesFragment.context, "Berhasil delete bookmark", Toast.LENGTH_SHORT).show()
                        bookmarked = false
                        binding.btnBookmark.text = "+ Bookmark"
                    }catch (e: Exception){
                        e.printStackTrace()
                        Toast.makeText(this@DetailFaskesFragment.context, "Gagal delete Bookmark", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    try {
                        viewModel.addBookmark(faskes, this@DetailFaskesFragment.context)
                        Toast.makeText(this@DetailFaskesFragment.context, "Berhasil bookmark", Toast.LENGTH_SHORT).show()
                        bookmarked = true
                        binding.btnBookmark.text = "- UnBookmark"
                    }catch (e: Exception){
                        e.printStackTrace()
                        Toast.makeText(this@DetailFaskesFragment.context, "Gagal Bookmark", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }

        return binding.root
//
    }
}