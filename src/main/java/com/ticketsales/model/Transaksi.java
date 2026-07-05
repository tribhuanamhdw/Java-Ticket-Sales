package com.ticketsales.model;

import java.sql.Timestamp;

public class Transaksi {
    private int id;
    private int tiketId;
    private int userId;
    private int jumlah;
    private double totalHarga;
    private Timestamp tanggalTransaksi;

    public Transaksi() {}

    public Transaksi(int id, int tiketId, int userId, int jumlah, double totalHarga, Timestamp tanggalTransaksi) {
        this.id = id;
        this.tiketId = tiketId;
        this.userId = userId;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTiketId() { return tiketId; }
    public void setTiketId(int tiketId) { this.tiketId = tiketId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public double getTotalHarga() { return totalHarga; }
    public void setTotalHarga(double totalHarga) { this.totalHarga = totalHarga; }
    public Timestamp getTanggalTransaksi() { return tanggalTransaksi; }
    public void setTanggalTransaksi(Timestamp tanggalTransaksi) { this.tanggalTransaksi = tanggalTransaksi; }
}
