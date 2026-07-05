package com.ticketsales.view;

import com.ticketsales.dao.TiketDAO;
import com.ticketsales.dao.TransaksiDAO;
import com.ticketsales.model.Tiket;
import com.ticketsales.model.Transaksi;
import com.ticketsales.model.User;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class TransaksiPanel extends JPanel {
    private JComboBox<String> cbTiket;
    private JTextField txtHarga, txtStok, txtJumlah, txtTotal;
    private JButton btnHitung, btnBeli;
    private JTable tableRiwayat;
    private DefaultTableModel tableModel;
    
    private User currentUser;
    private TiketDAO tiketDAO;
    private TransaksiDAO transaksiDAO;
    private List<Tiket> listTiket;

    public TransaksiPanel(User currentUser) {
        this.currentUser = currentUser;
        this.tiketDAO = new TiketDAO();
        this.transaksiDAO = new TransaksiDAO();

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Title
        JLabel lblHeader = new JLabel("Transaksi Pembelian Tiket");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(lblHeader, BorderLayout.NORTH);

        // --- TOP PANEL (FORM) ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(null, "Detail Pembelian", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Segoe UI", Font.BOLD, 15)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblPilih = new JLabel("Pilih Tiket:");
        lblPilih.setFont(new Font("Segoe UI", Font.BOLD, 13));
        formPanel.add(lblPilih, gbc);

        gbc.gridx = 1; gbc.gridwidth = 3;
        cbTiket = new JComboBox<>();
        cbTiket.setPreferredSize(new Dimension(300, 32));
        formPanel.add(cbTiket, gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        JLabel lblHarga = new JLabel("Harga Satuan:");
        lblHarga.setFont(new Font("Segoe UI", Font.BOLD, 13));
        formPanel.add(lblHarga, gbc);

        gbc.gridx = 1;
        txtHarga = new JTextField(15);
        txtHarga.setEditable(false);
        txtHarga.setPreferredSize(new Dimension(150, 32));
        formPanel.add(txtHarga, gbc);

        gbc.gridx = 2;
        JLabel lblStok = new JLabel("Stok Tersedia:");
        lblStok.setFont(new Font("Segoe UI", Font.BOLD, 13));
        formPanel.add(lblStok, gbc);

        gbc.gridx = 3;
        txtStok = new JTextField(10);
        txtStok.setEditable(false);
        txtStok.setPreferredSize(new Dimension(100, 32));
        formPanel.add(txtStok, gbc);

        // Row 3
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblJumlah = new JLabel("Jumlah Beli:");
        lblJumlah.setFont(new Font("Segoe UI", Font.BOLD, 13));
        formPanel.add(lblJumlah, gbc);

        gbc.gridx = 1;
        txtJumlah = new JTextField(15);
        txtJumlah.setPreferredSize(new Dimension(150, 32));
        txtJumlah.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Contoh: 2");
        formPanel.add(txtJumlah, gbc);

        gbc.gridx = 2;
        JLabel lblTotal = new JLabel("Total Harga:");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        formPanel.add(lblTotal, gbc);

        gbc.gridx = 3;
        txtTotal = new JTextField(15);
        txtTotal.setEditable(false);
        txtTotal.setPreferredSize(new Dimension(150, 32));
        formPanel.add(txtTotal, gbc);

        // Button Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnHitung = new JButton("🧮 Hitung Total");
        btnHitung.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnHitung.setPreferredSize(new Dimension(150, 40));
        
        btnBeli = new JButton("🛒 Konfirmasi Beli");
        btnBeli.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBeli.setBackground(new Color(0, 120, 215));
        btnBeli.setForeground(Color.WHITE);
        btnBeli.setPreferredSize(new Dimension(180, 40));
        btnBeli.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnPanel.add(btnHitung);
        btnPanel.add(btnBeli);

        JPanel topContainer = new JPanel(new BorderLayout(0, 10));
        topContainer.add(formPanel, BorderLayout.CENTER);
        topContainer.add(btnPanel, BorderLayout.SOUTH);
        
        add(topContainer, BorderLayout.NORTH);

        // --- BOTTOM PANEL (TABLE HISTORY) ---
        tableModel = new DefaultTableModel(new String[]{"ID Transaksi", "Nama Tiket", "Pembeli", "Jumlah", "Total Harga", "Waktu Transaksi"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableRiwayat = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(tableRiwayat);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(null, "Riwayat Transaksi", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Segoe UI", Font.BOLD, 15)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        add(scrollPane, BorderLayout.CENTER);

        // Listeners
        cbTiket.addActionListener(e -> {
            int index = cbTiket.getSelectedIndex();
            if (index >= 0 && index < listTiket.size()) {
                Tiket t = listTiket.get(index);
                txtHarga.setText(String.valueOf(t.getHarga()));
                txtStok.setText(String.valueOf(t.getStokTiket()));
                txtJumlah.setText("");
                txtTotal.setText("");
            }
        });

        btnHitung.addActionListener(e -> hitungTotal());
        btnBeli.addActionListener(e -> prosesBeli());

        refreshData();
    }

    public void refreshData() {
        listTiket = tiketDAO.getAllTiket();
        cbTiket.removeAllItems();
        for (Tiket t : listTiket) {
            cbTiket.addItem(t.getNamaTiket() + " (Stok: " + t.getStokTiket() + ")");
        }
        if(!listTiket.isEmpty()) {
            cbTiket.setSelectedIndex(0);
        }
        
        tableModel.setRowCount(0);
        List<Object[]> riwayat = transaksiDAO.getRiwayatTransaksi();
        for(Object[] row : riwayat) {
            tableModel.addRow(row);
        }
    }

    private void hitungTotal() {
        try {
            int jumlah = Integer.parseInt(txtJumlah.getText());
            double harga = Double.parseDouble(txtHarga.getText());
            double total = jumlah * harga;
            txtTotal.setText(String.valueOf(total));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah beli berupa angka yang valid!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void prosesBeli() {
        hitungTotal(); 
        if(txtTotal.getText().isEmpty()) return;
        
        int index = cbTiket.getSelectedIndex();
        if (index < 0) return;
        
        Tiket t = listTiket.get(index);
        int jumlah;
        try {
            jumlah = Integer.parseInt(txtJumlah.getText());
        } catch (NumberFormatException ex) {
            return;
        }
        double total = Double.parseDouble(txtTotal.getText());

        if (jumlah > t.getStokTiket()) {
            JOptionPane.showMessageDialog(this, "Stok tidak mencukupi! Sisa stok: " + t.getStokTiket(), "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Transaksi trans = new Transaksi(0, t.getId(), currentUser.getId(), jumlah, total, new Timestamp(System.currentTimeMillis()));
        
        if (transaksiDAO.insertTransaksi(trans)) {
            JOptionPane.showMessageDialog(this, "Berhasil! Transaksi sebesar Rp " + total + " telah disimpan.");
            refreshData(); 
        } else {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan pada transaksi atau stok telah habis.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
