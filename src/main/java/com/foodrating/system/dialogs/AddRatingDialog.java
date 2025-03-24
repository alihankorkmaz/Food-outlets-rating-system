package com.foodrating.system.dialogs;

import com.foodrating.system.dao.FoodOutletDAO;
import com.foodrating.system.models.FoodOutlet;
import com.foodrating.system.models.Rating;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddRatingDialog extends JDialog {

    private final FoodOutletDAO foodOutletDAO;
    private JComboBox<FoodOutlet> outletComboBox;
    private JTextField ratingField;

    public AddRatingDialog() {
        foodOutletDAO = new FoodOutletDAO();

        setSize(700, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Add Rating");
        initializeUI();
    }

    private void initializeUI() {
        Container container = getContentPane();
        container.setLayout(new GridLayout(4, 1));

        JLabel instructionLabel = new JLabel("Select a Food Outlet and enter a rating (1-5):");
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(instructionLabel);

        List<FoodOutlet> outlets = foodOutletDAO.getAllFoodOutlets();
        outletComboBox = new JComboBox<>(outlets.toArray(new FoodOutlet[0]));
        container.add(outletComboBox);

        JPanel ratingPanel = new JPanel();
        JLabel ratingLabel = new JLabel("Rating (1-5): ");
        ratingField = new JTextField(10);
        ratingPanel.add(ratingLabel);
        ratingPanel.add(ratingField);
        container.add(ratingPanel);

        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit Rating");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        container.add(buttonPanel);

        submitButton.addActionListener(e -> submitRating());
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void submitRating() {
        try {
            FoodOutlet selectedOutlet = (FoodOutlet) outletComboBox.getSelectedItem();
            if (selectedOutlet == null) {
                JOptionPane.showMessageDialog(this, "No Food Outlet selected!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int ratingValue = Integer.parseInt(ratingField.getText().trim());
            if (ratingValue < 1 || ratingValue > 5) {
                JOptionPane.showMessageDialog(this, "Rating must be between 1 and 5.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Rating rating = new Rating();
            rating.setFoodOutlet(selectedOutlet);
            rating.setRatingValue(ratingValue);

            new FoodOutletDAO().addRating(rating);

            JOptionPane.showMessageDialog(this, "Rating added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid rating value entered. Please enter a number between 1-5.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
