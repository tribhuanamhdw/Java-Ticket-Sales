package com.ticketsales.model;

import java.sql.Date;

public class Tiket {
    private int id;
    private String namaTiket;
    private String kategori;
    private double harga;
    private int stokTiket;
    private Date tanggalEvent;

    public Tiket() {}

    public Tiket(int id, String namaTiket, String kategori, double harga, int stokTiket, Date tanggalEvent) {
        this.id = id;
        this.namaTiket = namaTiket;
        this.kategori = kategori;
        this.harga = harga;
        this.stokTiket = stokTiket;
        this.tanggalEvent = tanggalEvent;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNamaTiket() { return namaTiket; }
    public void setNamaTiket(String namaTiket) { this.namaTiket = namaTiket; }
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }
    public int getStokTiket() { return stokTiket; }
    public void setStokTiket(int stokTiket) { this.stokTiket = stokTiket; }
    public Date getTanggalEvent() { return tanggalEvent; }
    public void setTanggalEvent(Date tanggalEvent) { this.tanggalEvent = tanggalEvent; }
}
