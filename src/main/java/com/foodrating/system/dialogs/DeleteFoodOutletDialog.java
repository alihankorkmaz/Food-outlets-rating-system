package com.foodrating.system.dialogs;

import com.foodrating.system.dao.FoodOutletDAO;
import com.foodrating.system.models.FoodOutlet;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeleteFoodOutletDialog extends JDialog {
    private JComboBox<FoodOutlet> foodOutletComboBox;
    private FoodOutletDAO foodOutletDAO;

    public DeleteFoodOutletDialog() {
        setTitle("Delete Food Outlet");
        setSize(700, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        foodOutletDAO = new FoodOutletDAO();
        initializeUI();
    }

    private void initializeUI() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Dropdown ve Açıklama
        JLabel label = new JLabel("Select a Food Outlet to delete:");
        panel.add(label, BorderLayout.NORTH);

        // FoodOutlet Seçim Kutusu
        foodOutletComboBox = new JComboBox<>();
        populateFoodOutletComboBox();
        panel.add(foodOutletComboBox, BorderLayout.CENTER);

        // Butonlar
        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Buton Aksiyonları
        deleteButton.addActionListener(e -> deleteFoodOutlet());
        cancelButton.addActionListener(e -> dispose());

        add(panel);
    }

    private void populateFoodOutletComboBox() {
        try {
            List<FoodOutlet> outlets = foodOutletDAO.getAllFoodOutlets();
            for (FoodOutlet outlet : outlets) {
                foodOutletComboBox.addItem(outlet);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load outlets: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteFoodOutlet() {
        FoodOutlet selectedOutlet = (FoodOutlet) foodOutletComboBox.getSelectedItem();
        if (selectedOutlet != null) {
            int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete " + selectedOutlet.getName() + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    foodOutletDAO.deleteFoodOutlet(selectedOutlet.getId());
                    JOptionPane.showMessageDialog(this, "Food outlet deleted successfully.");
                    dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Failed to delete outlet: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No food outlet selected.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
