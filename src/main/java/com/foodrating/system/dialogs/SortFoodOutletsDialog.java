package com.foodrating.system.dialogs;

import com.foodrating.system.dao.FoodOutletDAO;
import com.foodrating.system.models.FoodOutlet;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SortFoodOutletsDialog extends JDialog {
    private JTable table;
    private FoodOutletDAO foodOutletDAO;

    public SortFoodOutletsDialog() {
        setTitle("Sort Food Outlets by Rating");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        foodOutletDAO = new FoodOutletDAO();
        initializeUI();
    }

    private void initializeUI() {
        List<FoodOutlet> sortedOutlets = fetchSortedOutlets();

        String[] columnNames = {"Name", "Address", "Type", "Average Rating"};
        Object[][] data = new Object[sortedOutlets.size()][4];

        for (int i = 0; i < sortedOutlets.size(); i++) {
            FoodOutlet outlet = sortedOutlets.get(i);
            data[i][0] = outlet.getName();
            data[i][1] = outlet.getAddress();
            data[i][2] = outlet.getType();
            data[i][3] = outlet.getAverageRating() != 0 ? outlet.getAverageRating() : "N/A";
        }

        table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private List<FoodOutlet> fetchSortedOutlets() {
        try {
            return foodOutletDAO.getSortedOutletsByRating();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to fetch sorted outlets: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return List.of();
        }
    }
}
