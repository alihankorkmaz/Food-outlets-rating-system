package com.foodrating.system.dialogs;

import com.foodrating.system.dao.FoodOutletDAO;
import com.foodrating.system.models.FoodOutlet;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EditFoodOutletDialog {
    private JFrame frame;
    private JTextField nameField;
    private JTextField typeField;
    private JTextField addressField;
    private JComboBox<Long> idComboBox;

    private final FoodOutletDAO foodOutletDAO = new FoodOutletDAO();

    public EditFoodOutletDialog() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Edit Food Outlet");
        frame.setLayout(new BorderLayout());

        // Panel for form inputs
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        // Fields
        formPanel.add(new JLabel("Select Outlet ID:"));
        List<Long> outletIds = foodOutletDAO.getAllOutletIds();
        if (outletIds.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No outlets found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        idComboBox = new JComboBox<>(outletIds.toArray(new Long[0]));
        formPanel.add(idComboBox);

        formPanel.add(new JLabel("New Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("New Type:"));
        typeField = new JTextField();
        formPanel.add(typeField);

        formPanel.add(new JLabel("New Address:"));
        addressField = new JTextField();
        formPanel.add(addressField);

        // Add form panel to dialog
        frame.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save Changes");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add buttons panel to dialog
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        saveButton.addActionListener(e -> saveChanges());
        cancelButton.addActionListener(e -> frame.dispose());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void saveChanges() {
        try {
            Long selectedId = (Long) idComboBox.getSelectedItem();
            String newName = nameField.getText();
            String newType = typeField.getText();
            String newAddress = addressField.getText();

            if (selectedId == null || newName.isBlank() || newType.isBlank() || newAddress.isBlank()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            FoodOutlet updatedOutlet = new FoodOutlet();
            updatedOutlet.setId(selectedId);
            updatedOutlet.setName(newName);
            updatedOutlet.setType(newType);
            updatedOutlet.setAddress(newAddress);

            foodOutletDAO.updateOutlet(updatedOutlet);

            JOptionPane.showMessageDialog(frame, "Food outlet updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error updating food outlet: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
