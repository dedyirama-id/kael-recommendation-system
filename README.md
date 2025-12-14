# Sistem Rekomendasi Berbasis Graph

**Mata kuliah:** Algoritma dan Struktur Data  
**Kelompok:** D-2  
**Anggota:**

1. 5026241016 Christabel Arrowina Putri Tjahyadi
2. 5026241059 Nayla Rameyza Alya
3. 5026241083 Gusti Ayu Wedha Putri Surya
4. 5026241107 Dedy Irama

## Daftar Isi

- [Sistem Rekomendasi Berbasis Graph](#sistem-rekomendasi-berbasis-graph)
  - [Daftar Isi](#daftar-isi)
  - [1. Latar Belakang](#1-latar-belakang)
  - [2. Deskripsi Masalah](#2-deskripsi-masalah)
  - [3. Solusi](#3-solusi)
  - [4. Representasi Data, Struktur Data, Alur Program](#4-representasi-data-struktur-data-alur-program)
    - [4.1 Representasi Data dan Struktur Data](#41-representasi-data-dan-struktur-data)
      - [4.1.1 Entitas Utama: User, Event, Tag](#411-entitas-utama-user-event-tag)
        - [1. User](#1-user)
        - [2. Event](#2-event)
        - [3. Tag](#3-tag)
  - [5. Algoritma](#5-algoritma)
    - [5.1 BFS Recommendation](#51-bfs-recommendation)
    - [5.2 Personalized PageRank](#52-personalized-pagerank)
  - [6. Alur Program](#6-alur-program)
  - [7. Arsitektur Program](#7-arsitektur-program)
    - [File Utama](#file-utama)
      - [`Main.java`](#mainjava)
      - [`Screen.java`](#screenjava)
      - [`GraphLoader.java`](#graphloaderjava)
    - [Folder `entities/`](#folder-entities)
      - [`User.java`](#userjava)
      - [`Event.java`](#eventjava)
      - [`Tag.java`](#tagjava)
    - [Folder `models/`](#folder-models)
      - [`Graph.java`](#graphjava)
    - [Folder `algorithms/`](#folder-algorithms)
      - [`Personalization.java`](#personalizationjava)
      - [`Scored.java`](#scoredjava)
      - [`Sort.java`](#sortjava)
    - [Folder `utils/`](#folder-utils)
      - [`Terminal.java`](#terminaljava)
      - [`Text.java`](#textjava)
    - [Folder `views/`](#folder-views)
      - [`WellcomeScreen.java`](#wellcomescreenjava)
      - [`RecommendEventScreen.java`](#recommendeventscreenjava)
      - [`RecommendUserScreen.java`](#recommenduserscreenjava)
      - [`FindEventScreen.java`](#findeventscreenjava)
      - [`FindUserScreen.java`](#finduserscreenjava)
      - [`ShowGraphScreen.java`](#showgraphscreenjava)
    - [Folder `resources/`](#folder-resources)
  - [8. Cara Menjalankan Program](#8-cara-menjalankan-program)
  - [9. Daftar Kelompok]

## 1. Latar Belakang

Mahasiswa sering kesulitan menemukan event yang sesuai dengan minat, jurusan, serta tujuan pengembangan diri mereka. Hal ini disebabkan oleh informasi event yang tersebar di berbagai platform dan tidak terkurasi secara terpusat, sehingga banyak peluang penting akhirnya terlewat.

Ka’el awalnya dikembangkan dalam mata kuliah Manajemen Proyek Tangkas (MPT) sebagai sebuah platform yang mampu mengumpulkan berbagai event, mengelompokkannya berdasarkan topik dan kategori, serta menyediakan sistem rekomendasi yang menyesuaikan dengan profil setiap mahasiswa. Platform ini juga dirancang agar mudah diakses melalui Web maupun WhatsApp.

Untuk mendukung fitur rekomendasi tersebut, Ka’el memerlukan struktur data yang dapat memodelkan relasi antara user, tag, dan event, serta algoritma yang mampu menilai tingkat kedekatan dan relevansi event terhadap kebutuhan pengguna.

## 2. Deskripsi Masalah

- Event dan profil pengguna sama-sama memiliki informasi yang kaya, namun belum ada mekanisme otomatis yang mampu mencocokkan keduanya secara akurat. Proses pencarian manual tidak efisien dan sering membuat pengguna melewatkan event yang sebenarnya relevan dengan kebutuhan atau minatnya.
- Meski event dan profil pengguna sama-sama kaya informasi, tidak ada mekanisme otomatis yang mampu mencocokkan keduanya secara akurat. Pencarian manual sering tidak efisien dan dapat melewatkan event yang relevan.

**Bagaimana melakukan pencarian dan rekomendasi event yang relevan berdasarkan deskripsi profil pengguna secara efisien?**

## 3. Solusi

**Rekomendasi Event Menggunakan Graph dengan Modified BFS sebagai Sistem Scoring**  
Mengembangkan sistem rekomendasi berbasis graph dengan memodelkan event, tag, dan pengguna sebagai node. Hubungan antar entitas direpresentasikan sebagai edge. Semua node saling terhubung sehingga relevansi bisa dihitung berdasarkan kedekatan dalam graf.

## 4. Representasi Data, Struktur Data, Alur Program

### 4.1 Representasi Data dan Struktur Data

Secara konsep, program ini adalah sistem rekomendasi **berbasis graf** untuk **User, Event, dan Tag** yang ditampilkan lewat terminal.

#### 4.1.1 Entitas Utama: User, Event, Tag

Tiga entitas ini didefinisikan di package `org.kael.entities`.

##### 1. User

- Dipakai untuk merepresentasikan pengguna.
- Field:
  - `id`: ID unik user
  - `name`: nama user
  - `profile`: deskripsi profil
- Dipakai di:
  - `GraphLoader`: memuat user dari CSV user
  - `Graph`: user jadi vertex di graf
- `equals()` dan `hashCode()` berdasarkan `id` sehingga dua objek `User` dengan `id` yang sama akan dianggap sama.
- Kode:

  ```java
  public class User {
    private final String id;
    private final String name;
    private final String profile;
    ...
  }
  ```

##### 2. Event

- Representasi event yang direkomendasikan.
- Field: info dasar event (judul, deskripsi, tanggal, URL, organizer).
- Dipakai di:

  - `GraphLoader`: data dari `events.csv`
  - `Graph<Object>`: event jadi vertex
  - `RecommendEventScreen` dan `FindEventScreen`

- `equals()` dan `hashCode()` berdasarkan `id`.
- Kode:

  ```java
  public class Event {
    private final String id;
    private final String title;
    private final String slug;
    private final String description;
    private final String organizer;
    private final String startDate;
    private final String endDate;
    private final String url;
    ...
  }
  ```

##### 3. Tag

- Representasi tag/kategori/minat.
- Dipakai untuk menghubungkan user dan event melalui minat/hashtag.
- Dipakai di:

  - `GraphLoader`: data dari `tags.csv`
  - `Graph<Object>`: tag jadi vertex

- Kode:

  ```java
  public class Tag {
    private final String id;
    private final String name;
    private final String slug;
  ...
  }
  ```

## 5. Algoritma

Bagian ini menjelaskan dua algoritma utama yang digunakan (atau direncanakan) pada sistem rekomendasi Ka’el, yaitu algoritma BFS yang dimodifikasi untuk proses rekomendasi, dan algoritma Personalized PageRank (PPR).

### 5.1 BFS Recommendation

Algoritma BFS Recommendation digunakan untuk menjawab kebutuhan: bagaimana mencocokkan deskripsi profil pengguna dalam bentuk teks bebas dengan event-event yang relevan secara otomatis dan efisien.

Profil pengguna terlebih dahulu dikonversi menjadi beberapa simpul awal (_starting nodes_) di dalam graf, misalnya node tag atau event yang mengandung kata kunci dari profil tersebut. Dari kumpulan _starting nodes_ ini, sistem menjalankan penelusuran Breadth-First Search (BFS) hingga kedalaman tertentu dan memberikan skor pada setiap node yang dilewati.

Secara garis besar, alur kerja BFS Recommendation adalah sebagai berikut:

- Profil pengguna dipecah menjadi kata-kata, lalu setiap kata dicocokkan dengan teks pada node graf (tag, event, dan entitas lain) untuk mendapatkan _starting nodes_.
- Untuk setiap _starting node_, algoritma BFS dijalankan level demi level hingga batas `maxDepth`. Setiap node yang berhasil dikunjungi dalam batas kedalaman ini diberi tambahan skor sebesar `1` pada sebuah map skor.
- Skor dari semua _starting nodes_ dijumlahkan pada map yang sama, sehingga node yang sering tercapai dari berbagai _starting nodes_ akan memiliki skor total yang lebih tinggi.
- Setelah penelusuran selesai, skor dinormalisasi sehingga totalnya menjadi `1` dan dapat dipahami sebagai distribusi “peluang relevansi” terhadap profil pengguna.
- Map skor kemudian diubah menjadi daftar, diurutkan dari skor tertinggi, lalu hanya node yang mewakili event yang diambil sebagai Top-N rekomendasi.

Dengan pendekatan ini, BFS Recommendation tidak sekadar mencari event berdasarkan kecocokan kata kunci, tetapi juga mempertimbangkan kedekatan struktural di dalam graf user–tag–event. Hal ini membuat proses pencarian menjadi otomatis, terarah, dan lebih efisien dibandingkan pencarian manual.

### 5.2 Personalized PageRank

Personalized PageRank (PPR) disiapkan sebagai pengembangan dari BFS Recommendation untuk memberikan rekomendasi yang lebih kaya dan sensitif terhadap struktur global graf, namun tetap terpersonalisasi terhadap profil pengguna tertentu.

Berbeda dengan BFS yang fokus pada kedekatan lokal dalam radius kedalaman tertentu, PPR memodelkan proses _random walk_ di sepanjang graf dengan kemungkinan _restart_ ke node-node yang mewakili preferensi pengguna.

Intuisi kerjanya adalah sebagai berikut: bayangkan seorang “walker” yang berjalan di graf. Pada setiap langkah, dengan probabilitas tertentu walker mengikuti edge ke tetangga secara acak, dan dengan probabilitas lain ia “kembali” ke sekumpulan node yang menggambarkan profil user (vektor personalisasi).

Node yang sering dikunjungi dalam jangka panjang akan memperoleh skor PPR tinggi, dan skor ini diinterpretasikan sebagai ukuran relevansi node tersebut terhadap profil pengguna.

Secara implementasi, sistem menyusun vektor personalisasi dari node-node yang relevan dengan profil user, menginisialisasi vektor peringkat awal, lalu melakukan iterasi pembaruan nilai peringkat sampai konvergen: sebagian skor dialokasikan untuk _restart_ ke profil user, dan sisanya didistribusikan merata ke tetangga melalui edge yang ada.

Nilai akhir Personalized PageRank kemudian digunakan sebagai skor rekomendasi, dengan cara mengurutkan node berdasarkan skor dan memilih node event dengan nilai tertinggi.

Melalui pendekatan ini, PPR mampu menemukan event yang relevan tidak hanya karena dekat secara langsung dengan profil user, tetapi juga karena berada pada posisi penting dalam struktur graf secara keseluruhan.

## 6. Alur Program

## 7. Arsitektur Program

Struktur program dibangun untuk memisahkan logika algoritma, model data, pemrosesan graf, antarmuka terminal, dan layar tampilan. Berikut penjelasan folder dan file utama pada proyek:

```txt
kael-recommendation-system/
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── org
    │   │       └── kael
    │   │           ├── Main.java
    │   │           ├── GraphLoader.java
    │   │           ├── Screen.java
    │   │           ├── algorithms
    │   │           │   ├── Personalization.java
    │   │           │   ├── Scored.java
    │   │           │   └── Sort.java
    │   │           ├── entities
    │   │           │   ├── User.java
    │   │           │   ├── Event.java
    │   │           │   └── Tag.java
    │   │           ├── models
    │   │           │   └── Graph.java
    │   │           ├── utils
    │   │           │   ├── Terminal.java
    │   │           │   └── Text.java
    │   │           └── views
    │   │               ├── WellcomeScreen.java
    │   │               ├── RecommendEventScreen.java
    │   │               ├── RecommendUserScreen.java
    │   │               ├── FindEventScreen.java
    │   │               ├── FindUserScreen.java
    │   │               └── ShowGraphScreen.java
    │   └── resources
    │       ├── users.csv
    │       ├── events.csv
    │       ├── tags.csv
    │       ├── user_event.csv
    │       ├── user_tag.csv
    │       └── event_tag.csv
    └── test
        └── java
```

### File Utama

#### `Main.java`

Entry point program. Menginisialisasi terminal, memuat graph dari dataset CSV melalui `GraphLoader`, lalu menjalankan layar awal (wellcome screen). Program CLI berjalan sepenuhnya melalui navigasi antarlayar.

#### `Screen.java`

Interface yang digunakan seluruh layar tampilan (UI).

#### `GraphLoader.java`

Modul yang bertanggung jawab membaca file CSV dari folder `resources`, membangun objek `User`, `Event`, dan `Tag`, lalu memetakan relasi antar-entitas ke dalam graph menggunakan `Graph<T>`.

### Folder `entities/`

Berisi representasi objek domain yang menjadi node dalam graph.

#### `User.java`

Mewakili data pengguna, berisi id, nama, dan deskripsi profil.

#### `Event.java`

Mewakili data event, termasuk id, judul, deskripsi, penyelenggara, tanggal, URL, dll.

#### `Tag.java`

Mewakili tag atau kategori, digunakan untuk memetakan kategori user dan event.

### Folder `models/`

#### `Graph.java`

Struktur graf generik yang mendukung penambahan vertex, edge, pencarian neighbor, dan utilitas lain. Graph digunakan sebagai representasi relasi antar user, event, dan tag. Mendukung graph berarah maupun tidak.

### Folder `algorithms/`

Berisi algoritma untuk proses penilaian dan pengurutan rekomendasi.

#### `Personalization.java`

Mengimplementasikan:

- Algoritma **BFS-based recommendation**
- Algoritma **Personalized PageRank (PPR)** untuk menentukan skor relevansi event berdasarkan deskripsi profil pengguna.

#### `Scored.java`

Wrapper generik yang menyimpan objek beserta skor relevansinya.

#### `Sort.java`

Implementasi selection sort, digunakan untuk mengurutkan hasil rekomendasi berdasarkan skor.

### Folder `utils/`

#### `Terminal.java`

Abstraksi untuk input dan output CLI. Mengatur:

- pembacaan input user,
- pembersihan layar,
- navigasi antar screen.

#### `Text.java`

Utility untuk format teks (warna, bold, highlight) agar tampilan CLI lebih mudah dibaca.

### Folder `views/`

Berisi seluruh layar antarmuka pengguna.

#### `WellcomeScreen.java`

Layar awal yang menampilkan menu utama program.

#### `RecommendEventScreen.java`

Mengelola proses rekomendasi event:

- menerima input profil,
- menjalankan BFS atau PPR,
- menampilkan hasil rekomendasi.

#### `RecommendUserScreen.java`

Mirip dengan event, tetapi memberi rekomendasi pengguna lain yang relevan.

#### `FindEventScreen.java`

Fitur pencarian event berdasarkan id event.

#### `FindUserScreen.java`

Fitur pencarian user berdasarkan id user.

#### `ShowGraphScreen.java`

Menampilkan graph berupa node dan daftar tetangganya.

### Folder `resources/`

Dataset berupa CSV:

- `users.csv`
- `events.csv`
- `tags.csv`
- `user_tag.csv`
- `event_tag.csv`
- `user_event.csv`

Seluruh file digunakan `GraphLoader` untuk membentuk graph **User–Tag–Event**.

## 8. Cara Menjalankan Program

> Jika menggunakan IntelliJ, klon repository melalui fitur _New Project from Version Control_ pada IntelliJ. Kemudian untuk menjalankan program, klik tombol `Run` pada IntelliJ.

1. Persiapan lingkungan

   - Pastikan sudah terpasang:

     - Java Development Kit (JDK) versi 23 (atau minimal versi yang kompatibel).
     - Apache Maven.

   - Pastikan perintah `java` dan `mvn` sudah dikenali di terminal (cek dengan `java -version` dan `mvn -version`).

2. Kloning dan buka project

   - Klon project ini:

     ```bash
     git clone https://github.com/dedyirama-id/kael-recommendation-system.git
     ```

   - Masuk ke folder project, misalnya:

     ```bash
     cd kael-recommendation-system
     ```

3. Kompilasi program

   - Jalankan perintah:

     ```bash
     mvn clean compile
     ```

   - Perintah ini akan mengompilasi seluruh kode sumber ke dalam folder `target`.

4. Menjalankan program

   - Setelah kompilasi berhasil, jalankan aplikasi dengan:

     ```bash
     mvn exec:java
     ```

   - Maven akan mengeksekusi kelas utama `org.kael.Main` sesuai konfigurasi di `pom.xml`.

5. Interaksi di terminal

   - Program akan menampilkan menu awal di terminal.
   - Gunakan input angka/teks sesuai instruksi di layar untuk:

     - melihat rekomendasi event,
     - melihat rekomendasi user,
     - mencari event/user,
     - atau menampilkan ringkasan graf.

   - Ikuti pesan seperti “Press ENTER to continue...” untuk berpindah layar.

## 9. Daftar Kelompok
D-1 :
Link :

D-2 : Sistem Rekomendasi Event Berbasis Graph
Link : https://github.com/dedyirama-id/kael-recommendation-system

D-3 : Smart Traffic Light Simulator
Link : https://github.com/Sudukk/FP_ASD_Traffic_Light_Simulation_FINAL

D-4 :
Link :

D-5 :
Link :

D-6 :
Link :

D-7 :
Link :

D-8 :
Link :

D-9 :
Link :

D-10 :
Link :

D-11 :
Link :

D-12 :
Link :

D-13 :
Link :

D-14 :
Link :
