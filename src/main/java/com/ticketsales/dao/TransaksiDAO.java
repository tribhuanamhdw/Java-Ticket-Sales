package com.ticketsales.dao;

import com.ticketsales.model.Transaksi;
import com.ticketsales.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {

    public boolean insertTransaksi(Transaksi transaksi) {
        Connection conn = null;
        PreparedStatement checkStokStmt = null;
        PreparedStatement updateStokStmt = null;
        PreparedStatement insertTransStmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            if(conn == null) return false;
            
            conn.setAutoCommit(false); // Start transaction
            
            // 1. Check current stock
            String checkStokQuery = "SELECT stok_tiket FROM tiket WHERE id = ?";
            checkStokStmt = conn.prepareStatement(checkStokQuery);
            checkStokStmt.setInt(1, transaksi.getTiketId());
            rs = checkStokStmt.executeQuery();
            
            if (rs.next()) {
                int stok = rs.getInt("stok_tiket");
                if (stok < transaksi.getJumlah()) {
                    conn.rollback();
                    return false; // Insufficient stock
                }
                
                // 2. Reduce stock
                String updateStokQuery = "UPDATE tiket SET stok_tiket = stok_tiket - ? WHERE id = ?";
                updateStokStmt = conn.prepareStatement(updateStokQuery);
                updateStokStmt.setInt(1, transaksi.getJumlah());
                updateStokStmt.setInt(2, transaksi.getTiketId());
                updateStokStmt.executeUpdate();
                
                // 3. Insert transaction
                String insertTransQuery = "INSERT INTO transaksi (tiket_id, user_id, jumlah, total_harga) VALUES (?, ?, ?, ?)";
                insertTransStmt = conn.prepareStatement(insertTransQuery);
                insertTransStmt.setInt(1, transaksi.getTiketId());
                insertTransStmt.setInt(2, transaksi.getUserId());
                insertTransStmt.setInt(3, transaksi.getJumlah());
                insertTransStmt.setDouble(4, transaksi.getTotalHarga());
                insertTransStmt.executeUpdate();
                
                conn.commit(); // End transaction
                return true;
            } else {
                conn.rollback();
                return false; // Ticket not found
            }
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            // Restore auto-commit and close statements
            try {
                if (rs != null) rs.close();
                if (checkStokStmt != null) checkStokStmt.close();
                if (updateStokStmt != null) updateStokStmt.close();
                if (insertTransStmt != null) insertTransStmt.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // We can use a simple class or Object[] for History list, 
    // but returning Object array allows us to mix Transaction data with Ticket Name
    public List<Object[]> getRiwayatTransaksi() {
        List<Object[]> list = new ArrayList<>();
        String query = "SELECT t.id, tik.nama_tiket, u.username, t.jumlah, t.total_harga, t.tanggal_transaksi " +
                       "FROM transaksi t " +
                       "JOIN tiket tik ON t.tiket_id = tik.id " +
                       "JOIN user u ON t.user_id = u.id ORDER BY t.tanggal_transaksi DESC";
        
        try {
            Connection conn = DBConnection.getConnection();
            if(conn == null) return list;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
             
            while (rs.next()) {
                Object[] row = new Object[] {
                    rs.getInt("id"),
                    rs.getString("nama_tiket"),
                    rs.getString("username"),
                    rs.getInt("jumlah"),
                    rs.getDouble("total_harga"),
                    rs.getTimestamp("tanggal_transaksi")
                };
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
