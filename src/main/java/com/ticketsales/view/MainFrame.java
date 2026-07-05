package com.ticketsales.view;

import com.ticketsales.model.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private User currentUser;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame(User user) {
        this.currentUser = user;
        setTitle("Sistem Informasi Penjualan Tiket - Dashboard");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menu Bar Styling
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 100, 200));
        menuBar.setOpaque(true);
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JMenu menuNavigasi = new JMenu("🎫 Menu Navigasi");
        menuNavigasi.setForeground(Color.WHITE);
        menuNavigasi.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        JMenu menuAkun = new JMenu("👤 Akun Anda");
        menuAkun.setForeground(Color.WHITE);
        menuAkun.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JMenuItem menuMasterTiket = new JMenuItem("Master Tiket");
        JMenuItem menuTransaksi = new JMenuItem("Transaksi Pembelian");
        JMenuItem menuAboutUs = new JMenuItem("Tentang Kelompok");
        JMenuItem menuLogout = new JMenuItem("Logout");

        menuNavigasi.add(menuMasterTiket);
        menuNavigasi.add(menuTransaksi);
        menuNavigasi.addSeparator();
        menuNavigasi.add(menuAboutUs);
        menuAkun.add(menuLogout);

        menuBar.add(menuNavigasi);
        menuBar.add(Box.createHorizontalGlue()); // Push Akun to right
        menuBar.add(menuAkun);
        setJMenuBar(menuBar);

        // CardLayout Panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(245, 245, 250)); // Light Gray Background

        // Panels
        MasterTiketPanel masterTiketPanel = new MasterTiketPanel();
        TransaksiPanel transaksiPanel = new TransaksiPanel(currentUser);
        AboutUsPanel aboutUsPanel = new AboutUsPanel();

        // Add Panels to CardLayout
        mainPanel.add(masterTiketPanel, "MasterTiket");
        mainPanel.add(transaksiPanel, "Transaksi");
        mainPanel.add(aboutUsPanel, "AboutUs");

        add(mainPanel, BorderLayout.CENTER);

        // Menu Action Listeners
        menuMasterTiket.addActionListener(e -> {
            masterTiketPanel.loadData();
            cardLayout.show(mainPanel, "MasterTiket");
        });
        menuTransaksi.addActionListener(e -> {
            transaksiPanel.refreshData();
            cardLayout.show(mainPanel, "Transaksi");
        });
        menuAboutUs.addActionListener(e -> cardLayout.show(mainPanel, "AboutUs"));
        
        menuLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                this.dispose();
            }
        });
        
        // Show default
        cardLayout.show(mainPanel, "MasterTiket");
    }
}
