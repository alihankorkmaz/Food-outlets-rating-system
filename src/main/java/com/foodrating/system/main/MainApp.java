package com.foodrating.system.main;

import javax.swing.*;
import java.awt.*;

import com.foodrating.system.dialogs.*;

public class MainApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().createAndShowGUI());
    }

    private void createAndShowGUI() {
        // Ana Çerçeve oluştur
        JFrame frame = new JFrame("Food Outlets Rating System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // create buttons
        JButton addFoodOutletButton = new JButton("Add Food Outlet");
        JButton listFoodOutletsButton = new JButton("List All Outlets");
        JButton sortFoodOutletsButton = new JButton("Sort Outlets by Rating");
        JButton editFoodOutletButton = new JButton("Edit Food Outlet");
        JButton deleteFoodOutletButton = new JButton("Delete Food Outlet");
        JButton addRatingButton = new JButton("Add Rating for Outlets");

        // Butonlara eylem dinleyicileri ekle
        addFoodOutletButton.addActionListener(e -> new AddFoodOutletDialog());
        listFoodOutletsButton.addActionListener(e -> new ListAllOutletsDialog());
        sortFoodOutletsButton.addActionListener(e -> new SortFoodOutletsDialog().setVisible(true));
        editFoodOutletButton.addActionListener(e -> new EditFoodOutletDialog());
        deleteFoodOutletButton.addActionListener(e -> new DeleteFoodOutletDialog().setVisible(true));
        addRatingButton.addActionListener(e -> new AddRatingDialog());

        // Butonları ekle
        mainPanel.add(addFoodOutletButton);
        mainPanel.add(listFoodOutletsButton);
        mainPanel.add(sortFoodOutletsButton);
        mainPanel.add(editFoodOutletButton);
        mainPanel.add(deleteFoodOutletButton);
        mainPanel.add(addRatingButton);

        // Paneli ve çerçeveyi göster
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

}
