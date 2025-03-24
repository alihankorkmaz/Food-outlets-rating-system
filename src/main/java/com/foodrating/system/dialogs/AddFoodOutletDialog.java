package com.foodrating.system.dialogs;

import javax.swing.*;
import java.awt.*;
import com.foodrating.system.dao.FoodOutletDAO;
import com.foodrating.system.models.FoodOutlet;

public class AddFoodOutletDialog extends JDialog {
    private JTextField nameField, typeField, addressField;

    public AddFoodOutletDialog() {
        setTitle("Add Food Outlet");
        setSize(400, 300);
        setModal(true);
        setLocationRelativeTo(null);

        // Form öğeleri
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Type (Restaurant/Bistro/Takeaway):"));
        typeField = new JTextField();
        panel.add(typeField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submitForm());

        add(panel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void submitForm() {
        String name = nameField.getText();
        String type = typeField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || type.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FoodOutlet foodOutlet = new FoodOutlet(name, type, address);
        boolean success = FoodOutletDAO.save(foodOutlet);

        if (success) {
            JOptionPane.showMessageDialog(this, "Food Outlet added successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add Food Outlet", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
