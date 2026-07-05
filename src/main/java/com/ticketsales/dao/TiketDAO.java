package com.ticketsales.dao;

import com.ticketsales.model.Tiket;
import com.ticketsales.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TiketDAO {

    public List<Tiket> getAllTiket() {
        List<Tiket> listTiket = new ArrayList<>();
        String query = "SELECT * FROM tiket";
        
        try {
            Connection conn = DBConnection.getConnection();
            if(conn == null) return listTiket;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
             
            while (rs.next()) {
                Tiket t = new Tiket(
                    rs.getInt("id"),
                    rs.getString("nama_tiket"),
                    rs.getString("kategori"),
                    rs.getDouble("harga"),
                    rs.getInt("stok_tiket"),
                    rs.getDate("tanggal_event")
                );
                listTiket.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listTiket;
    }

    public boolean insertTiket(Tiket tiket) {
        String query = "INSERT INTO tiket (nama_tiket, kategori, harga, stok_tiket, tanggal_event) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
             
            stmt.setString(1, tiket.getNamaTiket());
            stmt.setString(2, tiket.getKategori());
            stmt.setDouble(3, tiket.getHarga());
            stmt.setInt(4, tiket.getStokTiket());
            stmt.setDate(5, tiket.getTanggalEvent());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTiket(Tiket tiket) {
        String query = "UPDATE tiket SET nama_tiket=?, kategori=?, harga=?, stok_tiket=?, tanggal_event=? WHERE id=?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
             
            stmt.setString(1, tiket.getNamaTiket());
            stmt.setString(2, tiket.getKategori());
            stmt.setDouble(3, tiket.getHarga());
            stmt.setInt(4, tiket.getStokTiket());
            stmt.setDate(5, tiket.getTanggalEvent());
            stmt.setInt(6, tiket.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTiket(int id) {
        String query = "DELETE FROM tiket WHERE id=?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
             
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Tiket getTiketById(int id) {
        Tiket tiket = null;
        String query = "SELECT * FROM tiket WHERE id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
             
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tiket = new Tiket(
                    rs.getInt("id"),
                    rs.getString("nama_tiket"),
                    rs.getString("kategori"),
                    rs.getDouble("harga"),
                    rs.getInt("stok_tiket"),
                    rs.getDate("tanggal_event")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tiket;
    }
}
