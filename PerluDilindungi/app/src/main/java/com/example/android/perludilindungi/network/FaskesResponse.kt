package com.example.android.perludilindungi.network

data class FaskesResponse(
    val success: Boolean,
    val message: String,
    val count_total: Int,
    val data: List<Faskes>
)

data class Faskes(
    val id: Int,
    val kode: String,
    val nama: String,
    val kota: String,
    val provinsi: String,
    val alamat: String?,
    val latitude: String,
    val longitude: String,
    val telp: String?,
    val jenis_faskes: String,
    val kelas_rs: String,
    val status: String,
    val detail: List<DetailFaskes>,
    val source_data: String,
    var isBookmarked : Boolean = false
)

data class  DetailFaskes(
    val id: Int,
    val kode: String,
    val batch: String,
    val divaksin: Int,
    val divaksin_1: Int,
    val divaksin_2: Int,
    val batal_vaksin: Int,
    val batal_vaksin_1: Int,
    val batal_vaksin_2: Int,
    val pending_vaksin: Int,
    val pending_vaksin_1: Int,
    val pending_vaksin_2: Int,
    val tanggal: String
)