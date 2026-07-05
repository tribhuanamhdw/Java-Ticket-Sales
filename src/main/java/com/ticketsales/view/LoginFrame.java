package com.ticketsales.view;

import com.ticketsales.dao.UserDAO;
import com.ticketsales.model.User;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginFrame() {
        setTitle("Sistem Penjualan Tiket");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Background Biru Gelap / Gradasi untuk Frame utama
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(new Color(40, 50, 75)); 
        
        // Panel Form (Card Putih di tengah)
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(Color.WHITE);
        // Shadow effect using FlatLaf
        cardPanel.putClientProperty(FlatClientProperties.STYLE, 
            "arc: 20;"
        );
        cardPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("Login Administrator");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(30, 40, 60));
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        cardPanel.add(lblTitle, gbc);

        // Username Field
        gbc.gridy = 1; gbc.gridwidth = 1; gbc.insets = new Insets(10, 0, 5, 0);
        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cardPanel.add(lblUser, gbc);
        
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 15, 0);
        txtUsername = new JTextField(20);
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan username");
        txtUsername.setPreferredSize(new Dimension(250, 35));
        cardPanel.add(txtUsername, gbc);

        // Password Field
        gbc.gridy = 3; gbc.insets = new Insets(5, 0, 5, 0);
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cardPanel.add(lblPass, gbc);
        
        gbc.gridy = 4; gbc.insets = new Insets(0, 0, 30, 0);
        txtPassword = new JPasswordField(20);
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan password");
        txtPassword.setPreferredSize(new Dimension(250, 35));
        cardPanel.add(txtPassword, gbc);

        // Login Button
        gbc.gridy = 5; gbc.insets = new Insets(10, 0, 0, 0);
        btnLogin = new JButton("Login Masuk");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnLogin.setPreferredSize(new Dimension(250, 40));
        btnLogin.setBackground(new Color(0, 120, 215)); // Primary Blue
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cardPanel.add(btnLogin, gbc);

        // Add card to background
        backgroundPanel.add(cardPanel);
        
        setContentPane(backgroundPanel);

        // Listeners
        btnLogin.addActionListener(e -> prosesLogin());
        txtPassword.addActionListener(e -> prosesLogin()); // Press enter on password
    }

    private void prosesLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if(username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan Password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(username, password);

        if (user != null) {
            new MainFrame(user).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username atau Password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
}
