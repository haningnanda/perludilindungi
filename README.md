# IF3210-2022-Android-15

Aplikasi ini adalah sebuah aplikasi untuk memantau berita terkini mengenai covid, mencari faskes terdekat untuk vaksin, dan untuk melakukan check in. Aplikasi ini dibuat untuk pemenuhan tugas mata kuliah IF3210 Pengembangan Aplikasi pada Platform Khusus. Aplikasi ini dibagun menggunakan bahasa Kotlin.

## Cara Kerja

### Fitur News

1. Pilih icon news pada botttom navigation bar. Saat pertama kali membuka aplikasi seharusnya sudah pada halaman news.
2. Setelah berita selesai di load, silahkan pilih berita yang ingin dibaca.
3. Setelah dipilih, link berita akan dibuka pada sebuah halaman baru.

### Fitur Lokasi Vaksin
1. Pilih icon lokasi vaksin pada bottom navigation bar.
2. Silakan memilih lokasi vaksin berupa provinsi dan kota.
3. Setelah dipilih, tekan tombol search untuk mendapatkan rekomendasi tempat yang terdekat. Pada halaman, ditampilkan 5 fasilitas kesehatan yang terdekat.
4. Untuk melihat detail fasilitas kesehatan, silakan pilih fasilitas kesehatan yang ingin dilihat detailnya.
5. Setelah dipilih, akan ditampilkan fasilitas kesehatan dan status kesiapan vaksinasi. Selain itu, terdapat tombol 'google maps' dan 'bookmark' yang mana tombol 'google maps' apabila ditekan berfungsi untuk melihat lokasi fasilitas kesehatan pada google maps dan tombol 'bookmark' berfungsi untuk menambah atau menghapus fasilitas kesehatan dari bookmark.

### Fitur Bookmark

### Fitur Check In


## Dependencies

### Retrofit

Retrofit digunakan untuk berkomunikasi dengan API backend. Dengan Library ini kami cukup membuat interface untuk setiap fungsi yang nantinya digunakan untuk melakukan get dan post terhadap API. Interface yang telah dibuat akan diimplemen oleh library ini dengan bantuan moshi untuk melakukan unmarshall JSON menjadi kotlin data.

### Moshi

Moshi digunakan bersamaan dengan retrofit unutk melakukan unmarshall JSON yang diterima dari API server menjadi kotlin data yang dapat digunakan aplikasi.

### Glide

Glide digunakan untuk mengambil resource gambar dari url. Library ini digunakan untuk load gambar thumbnail dari fitur news.

## Pembagian Kerja
13519028 - Hafid Abi Daniswara : Melakukan checkin, membuat fitur bookmark
13519029 - Nicholas Chen : Menampilkan list berita, menampilkan berita
13519042 - Haning Nanda Hapsari : Menampilkan lokasi fasilitas kesehatan, menampilkan detail fasilitas kesehatan, intent google maps