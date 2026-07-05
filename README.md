# Java-Ticket-Sales
Aplikasi desktop sistem informasi untuk pengelolaan penjualan tiket yang dibangun menggunakan Java dengan arsitektur Separation of Concerns (SoC).

# Deskripsi Proyek
Proyek ini merupakan sistem informasi penjualan tiket yang dikembangkan untuk memenuhi tugas mata kuliah [Sebutkan Nama Mata Kuliah]. Aplikasi ini memungkinkan admin untuk mengelola data tiket (Master Tiket) dan melakukan pencatatan penjualan tiket (Transaksi) secara terintegrasi dengan database MySQL.

# Fitur Utama
- Autentikasi: Sistem login aman dengan validasi username dan password dari database
- Manajemen Data Tiket (CRUD): Kelola stok dan informasi tiket secara efisien melalui antarmuka grafis
- Transaksi: Pencatatan penjualan dengan validasi stok otomatis. Sistem akan memberikan notifikasi (JOptionPane) jika stok tidak mencukup
- Navigasi: Antarmuka yang terstruktur dengan menu utama (JMenuBar)
- Keamanan: Implementasi PreparedStatement untuk mencegah SQL Injection
- Modularitas: Kode dikembangkan dengan prinsip OOP dan pemisahan logika melalui pola Data Access Object (DAO)

# Teknologi yang Digunakan
- Bahasa: Java (JDK 17+)
- GUI: Java Swing
- Database: MySQL
- Database Connector: MySQL Connector/J (JDBC)
- Build Tool: Apache Maven

# Struktur Proyek
/
├── src/main/java/com/ticketsales/
│   ├── dao/          # Logika akses database (CRUD)
│   ├── model/        # Kelas entitas (Tiket, Transaksi, User)
│   ├── util/         # Koneksi database (DBConnection)
│   └── view/         # Antarmuka grafis (JFrame, JPanel)
├── src/main/resources/img/ # Aset gambar profil anggota
├── database.sql      # Database dump
├── config.properties # Konfigurasi database (Custom)
└── pom.xml           # Konfigurasi dependensi Maven

# Cara Menjalankan Aplikasi
1. Persiapan Database:
- Buat database baru di MySQL dengan nama db_penjualan_tiket
- Import file database.sql ke dalam database tersebut
2. Konfigurasi:
- Salin config.properties.example menjadi config.properties
- Sesuaikan db.url, db.user, dan db.password di file config.properties dengan pengaturan MySQL Anda
3. Jalankan Aplikasi:
- Pastikan Maven sudah terpasang
- Jalankan melalui terminal: mvn exec:java atau gunakan file run.bat

# Anggota Kelompok
1. Anak Agung Tribhuana Mahadewi (210030394)
2. Ni Putu Tia Sucita Dewi (230030302)
   Proyek ini disusun sebagai bagian dari tugas akhir mata kuliah Pemrograman Berorientasi Objek.
