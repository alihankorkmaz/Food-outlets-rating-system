package com.foodrating.system.dialogs;

import com.foodrating.system.dao.FoodOutletDAO;
import com.foodrating.system.models.FoodOutlet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListAllOutletsDialog extends JDialog {

    public ListAllOutletsDialog() {
        setTitle("List of All Food Outlets");
        setSize(800, 600);
        setModal(true);
        setLocationRelativeTo(null);

        // Ana panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        JTable table = createOutletTable();
        JScrollPane scrollPane = new JScrollPane(table);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        mainPanel.add(closeButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JTable createOutletTable() {
        // Veritabanından liste çek
        List<FoodOutlet> outlets = FoodOutletDAO.getAllFoodOutlets();

        // Tablo sütunları
        String[] columnNames = {"ID", "Name", "Type", "Address", "Average Rating"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Listeyi tabloya ekle
        for (FoodOutlet outlet : outlets) {
            Object[] row = {
                    outlet.getId(),
                    outlet.getName(),
                    outlet.getType(),
                    outlet.getAddress(),
                    outlet.getAverageRating()
            };
            model.addRow(row);
        }

        return new JTable(model);
    }
}
