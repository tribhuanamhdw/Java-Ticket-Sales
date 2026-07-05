CREATE DATABASE IF NOT EXISTS db_penjualan_tiket;
USE db_penjualan_tiket;

CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'admin'
);

CREATE TABLE IF NOT EXISTS tiket (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_tiket VARCHAR(100) NOT NULL,
    kategori VARCHAR(50) NOT NULL,
    harga DOUBLE NOT NULL,
    stok_tiket INT NOT NULL,
    tanggal_event DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS transaksi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tiket_id INT NOT NULL,
    user_id INT NOT NULL,
    jumlah INT NOT NULL,
    total_harga DOUBLE NOT NULL,
    tanggal_transaksi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (tiket_id) REFERENCES tiket(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- Insert dummy data
INSERT IGNORE INTO user (id, username, password, role) VALUES 
(1, 'admin', 'admin123', 'admin');

INSERT IGNORE INTO tiket (id, nama_tiket, kategori, harga, stok_tiket, tanggal_event) VALUES
(1, 'Konser Dewa 19', 'Musik', 500000.0, 100, '2026-08-15'),
(2, 'Pertandingan Timnas', 'Olahraga', 250000.0, 50, '2026-07-20'),
(3, 'Standup Comedy', 'Hiburan', 150000.0, 200, '2026-09-01');
