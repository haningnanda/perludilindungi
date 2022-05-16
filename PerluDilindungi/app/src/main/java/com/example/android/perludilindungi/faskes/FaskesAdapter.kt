package com.example.android.perludilindungi.faskes

import GpsService
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.android.perludilindungi.R
import com.example.android.perludilindungi.bookmark.BookmarkFragmentDirections
import com.example.android.perludilindungi.lokasivaksin.LokasiVaksinFragment
import com.example.android.perludilindungi.lokasivaksin.LokasiVaksinFragmentDirections
import com.example.android.perludilindungi.network.Faskes

class FaskesAdapter (private val  data: List<Faskes>, fromBookmark: Boolean = false):
    RecyclerView.Adapter<FaskesAdapter.FaskesViewHolder>(){

    var fbm: Boolean
    init {
        fbm = fromBookmark
    }
    class FaskesViewHolder(itemView: View, fromBookmark: Boolean) : RecyclerView.ViewHolder(itemView){
        var judulFaskes: TextView = itemView.findViewById(R.id.judul_faskes)
        var jenisFaskes: TextView = itemView.findViewById(R.id.jenis_faskes)
        var alamatFaskes: TextView = itemView.findViewById(R.id.alamat_faskes)
        var teleponFaskes: TextView = itemView.findViewById(R.id.telepon_faskes)
        var kodeFaskes: TextView = itemView.findViewById(R.id.kode_faskes)
        var selectedProv:String = ""
        var selectedCity:String = ""
        var idFaskes:Int = 0
        init {
            itemView.setOnClickListener { view:View ->
                if (fromBookmark){
                    view.findNavController()
                        .navigate(
                            BookmarkFragmentDirections.actionBookmarkFragmentToDetailFaskesFragment(
                                idFaskes.toString(),selectedProv,selectedCity
                            )
                        )
                }else{
                    view.findNavController()
                        .navigate(
                            LokasiVaksinFragmentDirections
                            .actionLokasiVaksinFragmentToDetailFaskesFragment(idFaskes.toString(),selectedProv,selectedCity))
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaskesAdapter.FaskesViewHolder {
        return FaskesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_faskes,parent,false), fbm)
    }

    override fun onBindViewHolder(holder: FaskesViewHolder, position: Int) {
        val item = data[position]
        holder.judulFaskes.text = item.nama
        holder.jenisFaskes.text = item.jenis_faskes
        holder.alamatFaskes.text = item.alamat
        holder.teleponFaskes.text = item.telp
        holder.kodeFaskes.text = "Kode: ".plus(item.kode)
        holder.idFaskes = item.id
        holder.selectedProv = item.provinsi
        holder.selectedCity = item.kota
    }

    override fun getItemCount(): Int {
        if(fbm || data.size <= 5){
            return data.size
        }else{
            return 5
        }
    }

}