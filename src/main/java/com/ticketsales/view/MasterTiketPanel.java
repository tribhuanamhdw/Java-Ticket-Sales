package com.ticketsales.view;

import com.ticketsales.dao.TiketDAO;
import com.ticketsales.model.Tiket;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class MasterTiketPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtNama, txtKategori, txtHarga, txtStok, txtTanggal;
    private JButton btnTambah, btnUpdate, btnHapus, btnClear;
    private TiketDAO tiketDAO;

    public MasterTiketPanel() {
        tiketDAO = new TiketDAO();
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Title
        JLabel lblHeader = new JLabel("Manajemen Master Tiket");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(lblHeader, BorderLayout.NORTH);

        // --- LEFT PANEL (FORM) ---
        JPanel formPanelWrapper = new JPanel(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(null, "Form Data Tiket", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Segoe UI", Font.BOLD, 15)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 15, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Fields
        String[] labels = {"ID Tiket", "Nama Tiket", "Kategori", "Harga (Rp)", "Stok", "Tanggal (YYYY-MM-DD)"};
        JTextField[] fields = new JTextField[6];
        
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            gbc.weightx = 0;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            formPanel.add(lbl, gbc);

            gbc.gridx = 1; 
            gbc.weightx = 1.0;
            fields[i] = new JTextField();
            fields[i].setPreferredSize(new Dimension(200, 32));
            fields[i].putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan " + labels[i].toLowerCase());
            formPanel.add(fields[i], gbc);
        }

        txtId = fields[0]; txtId.setEditable(false); txtId.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Otomatis");
        txtNama = fields[1];
        txtKategori = fields[2];
        txtHarga = fields[3];
        txtStok = fields[4];
        txtTanggal = fields[5];

        // Button Panel
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        btnTambah = createButton("➕ Tambah", new Color(40, 167, 69), Color.WHITE);
        btnUpdate = createButton("✏️ Update", new Color(255, 193, 7), Color.BLACK);
        btnHapus = createButton("🗑️ Hapus", new Color(220, 53, 69), Color.WHITE);
        btnClear = createButton("🔄 Clear", new Color(108, 117, 125), Color.WHITE);
        
        btnPanel.add(btnTambah);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnHapus);
        btnPanel.add(btnClear);

        formPanelWrapper.add(formPanel, BorderLayout.CENTER);
        formPanelWrapper.add(btnPanel, BorderLayout.SOUTH);

        add(formPanelWrapper, BorderLayout.WEST);

        // --- RIGHT PANEL (TABLE) ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama Tiket", "Kategori", "Harga", "Stok", "Tanggal Event"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(scrollPane, BorderLayout.CENTER);

        // Listeners
        btnTambah.addActionListener(e -> tambahTiket());
        btnUpdate.addActionListener(e -> updateTiket());
        btnHapus.addActionListener(e -> hapusTiket());
        btnClear.addActionListener(e -> clearForm());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtNama.setText(tableModel.getValueAt(row, 1).toString());
                txtKategori.setText(tableModel.getValueAt(row, 2).toString());
                txtHarga.setText(tableModel.getValueAt(row, 3).toString());
                txtStok.setText(tableModel.getValueAt(row, 4).toString());
                txtTanggal.setText(tableModel.getValueAt(row, 5).toString());
            }
        });

        loadData();
    }
    
    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(100, 38));
        return btn;
    }

    public void loadData() {
        tableModel.setRowCount(0);
        List<Tiket> list = tiketDAO.getAllTiket();
        for (Tiket t : list) {
            tableModel.addRow(new Object[]{t.getId(), t.getNamaTiket(), t.getKategori(), t.getHarga(), t.getStokTiket(), t.getTanggalEvent()});
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        txtKategori.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        txtTanggal.setText("");
        table.clearSelection();
    }

    private void tambahTiket() {
        try {
            Tiket t = new Tiket(0, txtNama.getText(), txtKategori.getText(), 
                    Double.parseDouble(txtHarga.getText()), 
                    Integer.parseInt(txtStok.getText()), 
                    Date.valueOf(txtTanggal.getText()));
            if(tiketDAO.insertTiket(t)) {
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid! Pastikan format tanggal YYYY-MM-DD dan angka valid.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTiket() {
        if(txtId.getText().isEmpty()) return;
        try {
            Tiket t = new Tiket(Integer.parseInt(txtId.getText()), txtNama.getText(), txtKategori.getText(), 
                    Double.parseDouble(txtHarga.getText()), 
                    Integer.parseInt(txtStok.getText()), 
                    Date.valueOf(txtTanggal.getText()));
            if(tiketDAO.updateTiket(t)) {
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengupdate data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusTiket() {
        if(txtId.getText().isEmpty()) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus tiket ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            if(tiketDAO.deleteTiket(Integer.parseInt(txtId.getText()))) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
