package ui;

import database.database;
import items.Carriage;
import items.Locomotive;
import items.Track;
import items.Controller;
import items.Item.Gauge;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Catalog extends JFrame {
    public JPanel rootPanel;
    private JScrollPane itemsList;
    private JTable rows;
    private List<Object> allItemsInOrder;

    public Catalog(ResultSet items) throws SQLException {
        try {
            DefaultTableModel tableModel = new DefaultTableModel();

            // Create columns based on ResultSet metadata
            tableModel.addColumn("Brand");
            tableModel.addColumn("Name");
            tableModel.addColumn("Price");
            List<Locomotive> locomotives = new ArrayList<>();
            List<Carriage> carriages = new ArrayList<>();
            List<Track> track = new ArrayList<>();
            List<Controller> controllers = new ArrayList<>();
            // Populate the table model with data
            while (items.next()) {
                Object[] row = new Object[3]; // Three columns: brand, productName, price
                row[0] = items.getString("brand");
                row[1] = items.getString("productName");
                row[2] = items.getDouble("price");

                tableModel.addRow(row);
                if (items.getString("productCode").charAt(0) == 'L'){
                    locomotives.add(new Locomotive(Gauge.valueOf(items.getString("gauge")),
                            items.getString("era"), items.getString("brand"),
                            items.getString("productName"), items.getString("productCode"),
                            items.getDouble("price"), items.getInt("stockCount"),
                            items.getString("description")));
                } else if (items.getString("productCode").charAt(0) == 'C') {
                    controllers.add(new Controller(items.getString("brand"), items.getString("productName"),
                            items.getString("productCode"), items.getDouble("price"),
                            items.getInt("stockCount"), items.getString("description")));
                } else if (items.getString("productCode").charAt(0) == 'R') {
                    track.add(new Track(Gauge.valueOf(items.getString("gauge")),
                                    items.getString("brand"), items.getString("productName"),
                                    items.getString("productCode"), items.getDouble("price"),
                                    items.getInt("stockCount"), items.getString("description")));
                } else if (items.getString("productCode").charAt(0) == 'S') {
                    carriages.add(new Carriage(items.getString("era"),
                            Gauge.valueOf(items.getString("gauge")),
                            items.getString("brand"), items.getString("productName"),
                            items.getString("productCode"), items.getDouble("price"),
                            items.getInt("stockCount"), items.getString("description")));
                }
                List<Object> allItemsInOrder = new ArrayList<>();
                allItemsInOrder.add(locomotives);
                allItemsInOrder.add(controllers);
                allItemsInOrder.add(track);
                allItemsInOrder.add(carriages);
            }

            rows.setModel(tableModel);
            rows.setDefaultEditor(Object.class, null);
            itemsList.setViewportView(rows);

            rows.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int selectedRow = rows.getSelectedRow();
                        if (selectedRow != -1) {
                            displayItemInformation(selectedRow, allItemsInOrder);
                        }
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }
    private void displayItemInformation(int rowIndex, List<Object> allItems) {
        // Retrieve information about the selected item

        // Create a panel to hold the components
        JPanel panel = new JPanel(new GridLayout(0, 1));

        // Add labels with item information
        //panel.add(new JLabel("Item: " + productName));
        //panel.add(new JLabel("Price: $" + price));
        //panel.add(new JLabel("Description: " + description));

        // Add buttons for user interaction
        JButton addToBagButton = new JButton("Add to Bag");
        JButton cancelButton = new JButton("Cancel");

        panel.add(addToBagButton);
        panel.add(cancelButton);

        // ActionListener for the Add to Bag button
        addToBagButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your logic for adding the item to the shopping bag
                JOptionPane.showMessageDialog(null, "Item added to the shopping bag!");
            }
        });

        // ActionListener for the Cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // User chose not to add the item
                JOptionPane.showMessageDialog(null, "Operation canceled.");
            }
        });

        // Show the option pane with the custom panel
        int result = JOptionPane.showOptionDialog(
                this, // Parent component
                panel,
                "Item Information",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{},
                null);

        // Handle the result if needed
        if (result == JOptionPane.YES_OPTION) {
            // User clicked "Add to Bag"
            // Perform the action you want when the user adds the item
        } else {
            // User clicked "Cancel" or closed the dialog
            // Handle accordingly or do nothing
        }
    }
}


