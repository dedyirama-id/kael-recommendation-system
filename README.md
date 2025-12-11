# Sistem Rekomendasi Berbasis Graph

Mata kuliah : Algoritma dan Struktur Data  
Kelompok    : D-2  
Anggota     :  
  1. 5026241016 Christabel Arrowina Putri Tjahyadi	
  2. 5026241059 Nayla Rameyza Alya	
  3. 5026241083 Gusti Ayu Wedha Putri Surya
  4. 5026241107 Dedy Irama	

## 1. Latar Belakang
Mahasiswa sering kesulitan menemukan event yang sesuai dengan minat, jurusan, serta tujuan pengembangan diri mereka. Hal ini disebabkan oleh informasi event yang tersebar di berbagai platform dan tidak terkurasi secara terpusat, sehingga banyak peluang penting akhirnya terlewat. Ka’el awalnya dikembangkan dalam mata kuliah Manajemen Proyek Tangkas (MPT) sebagai sebuah platform yang mampu mengumpulkan berbagai event, mengelompokkannya berdasarkan topik dan kategori, serta menyediakan sistem rekomendasi yang menyesuaikan dengan profil setiap mahasiswa. Platform ini juga dirancang agar mudah diakses melalui Web maupun WhatsApp. Untuk mendukung fitur rekomendasi tersebut, Ka’el memerlukan struktur data yang dapat memodelkan relasi antara user, tag, dan event, serta algoritma yang mampu menilai tingkat kedekatan dan relevansi event terhadap kebutuhan pengguna. 

## 2. Deskripsi Masalah
- Event dan profil pengguna sama-sama memiliki informasi yang kaya, namun belum ada mekanisme otomatis yang mampu mencocokkan keduanya secara akurat. Proses pencarian manual tidak efisien dan sering membuat pengguna melewatkan event yang sebenarnya relevan dengan kebutuhan atau minatnya.
- Meski event dan profil pengguna sama-sama kaya informasi, tidak ada mekanisme otomatis yang mampu mencocokkan keduanya secara akurat. Pencarian manual sering tidak efisien dan dapat melewatkan event yang relevan.

**“Bagaimana melakukan pencarian dan rekomendasi event yang relevan berdasarkan deskripsi profil pengguna secara efisien?”**

## 3. Solusi
**Rekomendasi Event Menggunakan Graph dengan Modified BFS sebagai Sistem Scoring**
Mengembangkan sistem rekomendasi berbasis graph dengan memodelkan event, tag, dan pengguna sebagai node. Hubungan antar entitas direpresentasikan sebagai edge.  Semua node saling terhubung sehingga relevansi bisa dihitung berdasarkan kedekatan dalam graf.

## 4. Representasi Data, Struktur Data, Alur Program
## 4.1 Representasi Data & Struktur Data
Secara konsep, program ini adalah sistem rekomendasi **berbasis graf untuk User, Event, dan Tag yang ditampilkan lewat terminal.

**4.1.1 Entity Utama: User, Event, Tag**
Tiga entity ini didefinisikan di package org.kael.entities:
1. User 
>> public class User {
>> private final String id;
>> private final String name;
>> private final String profile;
>> ...
>> }
> 3. Event:
> 4. Tag:



## 5. Algoritma
Bagian ini menjelaskan dua algoritma utama yang digunakan (atau direncanakan) pada sistem rekomendasi Ka’el, yaitu algoritma BFS yang dimodifikasi untuk proses rekomendasi, dan algoritma Personalized PageRank (PPR).
### 5.1 BFS Recommendation
Algoritma BFS Recommendation digunakan untuk menjawab kebutuhan: bagaimana mencocokkan deskripsi profil pengguna dalam bentuk teks bebas dengan event-event yang relevan secara otomatis dan efisien. Profil pengguna terlebih dahulu dikonversi menjadi beberapa simpul awal (starting nodes) di dalam graf, misalnya node tag atau event yang mengandung kata kunci dari profil tersebut. Dari kumpulan starting nodes ini, sistem menjalankan penelusuran Breadth-First Search (BFS) hingga kedalaman tertentu dan memberikan skor pada setiap node yang dilewati.

Secara garis besar, alur kerja BFS Recommendation adalah sebagai berikut:
- Profil pengguna dipecah menjadi kata-kata, lalu setiap kata dicocokkan dengan teks pada node graf (tag, event, dan entitas lain) untuk mendapatkan starting nodes.
- Untuk setiap starting node, algoritma BFS dijalankan level demi level hingga batas maxDepth. Setiap node yang berhasil dikunjungi dalam batas kedalaman ini diberi tambahan skor sebesar 1 pada sebuah map skor.
- Skor dari semua starting nodes dijumlahkan pada map yang sama, sehingga node yang sering tercapai dari berbagai starting nodes akan memiliki skor total yang lebih tinggi.
- Setelah penelusuran selesai, skor dinormalisasi sehingga totalnya menjadi 1 dan dapat dipahami sebagai distribusi “peluang relevansi” terhadap profil pengguna.
- Map skor kemudian diubah menjadi daftar, diurutkan dari skor tertinggi, lalu hanya node yang mewakili event yang diambil sebagai Top-N rekomendasi.

Dengan pendekatan ini, BFS Recommendation tidak sekadar mencari event berdasarkan kecocokan kata kunci, tetapi mempertimbangkan juga kedekatan struktural di dalam graf user–tag–event. Hal ini membuat proses pencarian menjadi otomatis, terarah, dan lebih efisien dibandingkan pencarian manual.

### 5.2 Personalized PageRank
Personalized PageRank (PPR) disiapkan sebagai pengembangan dari BFS Recommendation untuk memberikan rekomendasi yang lebih kaya dan sensitif terhadap struktur global graf, namun tetap terpersonalisasi terhadap profil pengguna tertentu. Berbeda dengan BFS yang fokus pada kedekatan lokal dalam radius kedalaman tertentu, PPR memodelkan proses random walk di sepanjang graf dengan kemungkinan restart ke node-node yang mewakili preferensi pengguna.

Intuisi kerjanya adalah sebagai berikut: bayangkan seorang “walker” yang berjalan di graf. Pada setiap langkah, dengan probabilitas tertentu walker mengikuti edge ke tetangga secara acak, dan dengan probabilitas lain ia “kembali” ke sekumpulan node yang menggambarkan profil user (vektor personalisasi). Node yang sering dikunjungi dalam jangka panjang akan memperoleh skor PPR tinggi, dan skor ini diinterpretasikan sebagai ukuran relevansi node tersebut terhadap profil pengguna. Secara implementasi, sistem menyusun vektor personalisasi dari node-node yang relevan dengan profil user, menginisialisasi vektor peringkat awal, lalu melakukan iterasi pembaruan nilai peringkat sampai konvergen: sebagian skor dialokasikan untuk restart ke profil user, dan sisanya didistribusikan merata ke tetangga melalui edge yang ada. Nilai akhir Personalized PageRank kemudian digunakan sebagai skor rekomendasi, dengan cara mengurutkan node berdasarkan skor dan memilih node event dengan nilai tertinggi.

Melalui pendekatan ini, PPR mampu menemukan event yang relevan tidak hanya karena dekat secara langsung dengan profil user, tetapi juga karena berada pada posisi penting dalam struktur graf secara keseluruhan. Ini menjawab kebutuhan sistem untuk melakukan pencarian dan rekomendasi event yang relevan berdasarkan deskripsi profil pengguna secara otomatis dan efisien, terutama ketika jumlah user dan event semakin besar dan hubungan antar entitas menjadi lebih kompleks.

## 6. Alur Program

## 7. Arsitektur Program

## 8. Cara Menjalankan Program

## 9. Pengujian dan Evaluasi

## 10. Analisis Kompleksitas

## Referensi
Paliwal, J. (n.d.). Graph-Based Recommendation System [Kaggle Notebook]. Kaggle.
https://www.kaggle.com/code/jahnavipaliwal/graph-based-recommendation-system
(Accessed on: 11 Desember 2025)

