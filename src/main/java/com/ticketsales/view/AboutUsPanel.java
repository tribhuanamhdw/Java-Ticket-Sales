package com.ticketsales.view;

import com.formdev.flatlaf.FlatClientProperties;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class AboutUsPanel extends JPanel {

    public AboutUsPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel lblTitle = new JLabel("Tim Pengembang", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitle.setForeground(new Color(33, 37, 41));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel panelMembers = new JPanel(new GridLayout(1, 2, 30, 0));
        panelMembers.setOpaque(false);

        panelMembers.add(createMemberCard(
                "NIM : 210030394",
                "Anak Agung Tribhuana Mahadewi",
                "/img/Anak Agung Tribhuana Mahadewi.PNG"
        ));

        panelMembers.add(createMemberCard(
                "NIM : 230030302",
                "Ni Putu Tia Sucita Dewi",
                "/img/Ni Putu Tia Sucita Dewi.PNG"
        ));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(panelMembers);
        add(centerPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel(
                "Sistem Informasi Penjualan Tiket | Java Swing | JDBC | MySQL",
                SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        footer.setForeground(Color.GRAY);
        footer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel createMemberCard(String nim, String nama, String imagePath) {
        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(Color.WHITE);
        card.putClientProperty(FlatClientProperties.STYLE, "arc:20");
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblFoto = new JLabel("", SwingConstants.CENTER);
        lblFoto.setPreferredSize(new Dimension(150, 150));

        try {
            URL imgUrl = getClass().getResource(imagePath);
            if (imgUrl != null) {
                lblFoto.setIcon(new ImageIcon(createCircularImage(imgUrl, 150)));
            } else {
                showPlaceholder(lblFoto);
            }
        } catch (Exception e) {
            showPlaceholder(lblFoto);
        }

        JLabel lblNama = new JLabel(nama, SwingConstants.CENTER);
        lblNama.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblNama.setForeground(new Color(33, 37, 41));

        JLabel lblNim = new JLabel(nim, SwingConstants.CENTER);
        lblNim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNim.setForeground(Color.GRAY);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        infoPanel.add(lblNama);
        infoPanel.add(lblNim);

        card.add(lblFoto, BorderLayout.CENTER);
        card.add(infoPanel, BorderLayout.SOUTH);

        return card;
    }

    private void showPlaceholder(JLabel label) {
        label.setText("Foto");
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        label.setForeground(Color.LIGHT_GRAY);
    }

    private BufferedImage createCircularImage(URL imageUrl, int size) throws IOException {
        BufferedImage original = ImageIO.read(imageUrl);
        
        // 1. Buat gambar output dengan kualitas tinggi (ARGB untuk transparansi)
        BufferedImage output = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setClip(new Ellipse2D.Float(0, 0, size, size));

        int imgW = original.getWidth();
        int imgH = original.getHeight();
        int dim = Math.min(imgW, imgH);
        int x = (imgW - dim) / 2;
        int y = (imgH - dim) / 2;

        g2.drawImage(original, 0, 0, size, size, x, y, x + dim, y + dim, null);
        
        g2.dispose();
        return output;
    }
}