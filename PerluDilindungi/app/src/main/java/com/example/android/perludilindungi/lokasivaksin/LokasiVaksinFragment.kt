package com.example.android.perludilindungi.lokasivaksin

import GpsService
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.perludilindungi.R
import com.example.android.perludilindungi.bookmark.BookmarkFragment
import com.example.android.perludilindungi.bookmark.DbBookmarkHandler
import com.example.android.perludilindungi.databinding.FragmentLokasiVaksinBinding
import com.example.android.perludilindungi.databinding.FragmentNewsBinding
import com.example.android.perludilindungi.faskes.FaskesAdapter
import com.example.android.perludilindungi.faskes.FaskesViewModel
import com.example.android.perludilindungi.network.LocationProperty
import com.example.android.perludilindungi.network.PerluDilindungiApi
import com.example.android.perludilindungi.news.NewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LokasiVaksinFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var listKeyProv = ArrayList<String>()
    private var listNamaProv = ArrayList<String>()
    private var listKeyKota = ArrayList<String>()
    private var listNamaKota = ArrayList<String>()
    var selectedProvince = String()
    var selectedCity = String()
    lateinit var gpsService: GpsService

    private var _binding : FragmentLokasiVaksinBinding? = null
    private val binding get() = _binding!!
    lateinit var dbBookmarkHandler: DbBookmarkHandler
    private val viewModel: FaskesViewModel by lazy {
        ViewModelProvider(this).get(FaskesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gpsService = GpsService(this.requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLokasiVaksinBinding.inflate(inflater, container, false)
        dbBookmarkHandler = DbBookmarkHandler.getInstance(this@LokasiVaksinFragment.context)
        viewModel.getBookmark(this@LokasiVaksinFragment.context)
        showProvinsi()
        binding.btnSearch.setOnClickListener {
            binding.dataFaskes.layoutManager = LinearLayoutManager(this.context)
            binding.dataFaskes.setHasFixedSize(true)

            binding.lifecycleOwner = this
            binding.faskesViewModel = viewModel

            viewModel.getFaskes(selectedProvince,selectedCity)

            viewModel.faskes.observe(viewLifecycleOwner){ faskes ->
                val sorted = faskes.sortedBy {gpsService.getDistance(it.latitude.toDouble(),it.longitude.toDouble())}
                val adapter = FaskesAdapter(sorted)
                binding.dataFaskes.adapter = adapter
            }

        }
        return binding.root
    }
    private fun showProvinsi() {
        PerluDilindungiApi.retrofitService.getProvince().enqueue(object :
            Callback<LocationProperty> {
            override fun onResponse(
                call: Call<LocationProperty>,
                response: Response<LocationProperty>
            ) {
                val listResponse = response.body()?.results
                listResponse?.forEach {
                    listKeyProv.add(it.key)
                    listNamaProv.add(it.value)
                }
                val adapter = this@LokasiVaksinFragment.context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, listNamaProv) }
                binding.spProvinsi.adapter = adapter

                binding.spProvinsi.onItemSelectedListener = this@LokasiVaksinFragment
            }

            override fun onFailure(call: Call<LocationProperty>, t: Throwable) {
                Toast.makeText(this@LokasiVaksinFragment.context, "${t.message}", Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun showKota(key: String) {
        PerluDilindungiApi.retrofitService.getCity(key).enqueue(object :
            Callback<LocationProperty> {
            override fun onResponse(
                call: Call<LocationProperty>,
                response: Response<LocationProperty>
            ) {
                val listResponse = response.body()?.results
                listKeyKota.clear()
                listNamaKota.clear()
                listResponse?.forEach {
                    listKeyKota.add(it.key)
                    listNamaKota.add(it.value)
                }
                val adapter = this@LokasiVaksinFragment.context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, listNamaKota) }
                binding.spKota.adapter = adapter
                binding.spKota.onItemSelectedListener = this@LokasiVaksinFragment

            }

            override fun onFailure(call: Call<LocationProperty>, t: Throwable) {
                Toast.makeText(this@LokasiVaksinFragment.context, "${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent?.getItemAtPosition(position)
        if (parent?.selectedItem == binding.spProvinsi.selectedItem){
            selectedProvince = binding.spProvinsi.selectedItem.toString()
            showKota(listKeyProv[position])
        } else if (parent?.selectedItem == binding.spKota.selectedItem){
            selectedCity = binding.spKota.selectedItem.toString()
        }
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}