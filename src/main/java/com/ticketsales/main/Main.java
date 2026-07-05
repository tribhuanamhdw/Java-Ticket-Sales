package com.ticketsales.main;

import com.ticketsales.view.LoginFrame;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Set Look and Feel to FlatLaf for modern UI
        try {
            FlatLightLaf.setup();
            
            // Global UI properties for FlatLaf
            UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
            
            // Table Styling
            UIManager.put("Table.rowHeight", 32);
            UIManager.put("Table.showHorizontalLines", true);
            UIManager.put("Table.showVerticalLines", false);
            UIManager.put("Table.alternateRowColor", new Color(245, 245, 250));
            UIManager.put("Table.selectionBackground", new Color(200, 225, 255));
            UIManager.put("Table.selectionForeground", Color.BLACK);
            
            UIManager.put("TableHeader.background", new Color(230, 235, 245));
            UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 14));
            
            // Button Styling
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
