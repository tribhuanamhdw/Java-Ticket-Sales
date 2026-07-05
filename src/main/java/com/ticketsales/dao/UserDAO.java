package com.ticketsales.dao;

import com.ticketsales.model.User;
import com.ticketsales.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    
    public User login(String username, String password) {
        User user = null;
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        
        try {
            Connection conn = DBConnection.getConnection();
            if(conn == null) return null;
            PreparedStatement stmt = conn.prepareStatement(query);
             
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }
}
