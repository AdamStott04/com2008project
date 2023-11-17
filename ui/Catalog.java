package ui;

import database.database;
import items.Carriage;
import items.Locomotive;
import items.Track;
import items.Controller;
import items.TrackPack;
import items.TrainSet;
import items.Item;
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
    private List<Item> allItemsInOrder;
    private List<Locomotive> locomotives;
    private List<Carriage> carriages;
    private List<Track> track;
    private List<Controller> controllers;

    private List<TrackPack> trackpack;

    private List<TrainSet> trainset;

    public Catalog(ResultSet items) throws SQLException {
        try {
            DefaultTableModel tableModel = new DefaultTableModel();

            // Create columns based on ResultSet metadata
            tableModel.addColumn("Brand");
            tableModel.addColumn("Name");
            tableModel.addColumn("Price");

            allItemsInOrder = new ArrayList<>();

            // Populate the table model with data
            while (items.next()) {
                Object[] row = new Object[3]; // Three columns: brand, productName, price
                row[0] = items.getString("brand");
                row[1] = items.getString("productName");
                row[2] = items.getDouble("price");

                tableModel.addRow(row);
                if (items.getString("productCode").charAt(0) == 'L'){
                    Locomotive locomotive = new Locomotive(Gauge.valueOf(items.getString("gauge")),
                            items.getString("era"), items.getString("brand"),
                            items.getString("productName"), items.getString("productCode"),
                            items.getDouble("price"), items.getInt("stockCount"),
                            items.getString("description"));
                    allItemsInOrder.add(locomotive);
                } else if (items.getString("productCode").charAt(0) == 'C') {
                    Controller controller = new Controller(items.getString("brand"), items.getString("productName"),
                            items.getString("productCode"), items.getDouble("price"),
                            items.getInt("stockCount"), items.getString("description"));
                    allItemsInOrder.add(controller);
                } else if (items.getString("productCode").charAt(0) == 'R') {
                    Track newTrack = new Track(Gauge.valueOf(items.getString("gauge")),
                                    items.getString("brand"), items.getString("productName"),
                                    items.getString("productCode"), items.getDouble("price"),
                                    items.getInt("stockCount"), items.getString("description"));
                    allItemsInOrder.add(newTrack);
                } else if (items.getString("productCode").charAt(0) == 'S') {
                    Carriage carriage = new Carriage(items.getString("era"),
                            Gauge.valueOf(items.getString("gauge")),
                            items.getString("brand"), items.getString("productName"),
                            items.getString("productCode"), items.getDouble("price"),
                            items.getInt("stockCount"), items.getString("description"));
                    allItemsInOrder.add(carriage);
                }
                  else if (items.getString("productCode").charAt(0) == 'P') {
                    TrackPack trackpack = new TrackPack(Gauge.valueOf(items.getString("gauge")),
                            items.getString("brand"), items.getString("productName"),
                            items.getString("productCode"), items.getDouble("price"),
                            items.getInt("stockCount"), items.getString("description"));
                    allItemsInOrder.add(trackpack);
                }
                  else if (items.getString("productCode").charAt(0) == 'M'){
                    TrainSet trainset = new TrainSet(Gauge.valueOf(items.getString("gauge")),
                            items.getString("era"), items.getString("brand"),
                            items.getString("productName"), items.getString("productCode"),
                            items.getDouble("price"), items.getInt("stockCount"),
                            items.getString("description"));
                    allItemsInOrder.add(trainset);
                }
            }

            rows.setModel(tableModel);
            rows.setDefaultEditor(Object.class, null);
            itemsList.setViewportView(rows);

            System.out.println("Order in table model:");
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                System.out.println(tableModel.getValueAt(i, 0) + ", " +
                        tableModel.getValueAt(i, 1) + ", " +
                        tableModel.getValueAt(i, 2));
            }

            System.out.println("Order in allItemsInOrder:");
            for (Item item : allItemsInOrder) {
                System.out.println(item.getBrand() + ", " +
                        item.getName() + ", " +
                        item.getPrice());
            }

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
    private void displayItemInformation(int rowIndex, List<Item> allItems) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        // Retrieve information about the selected item
        System.out.println(rowIndex);
        System.out.println(allItems.get(rowIndex));
        Item selectedItem = allItems.get(rowIndex);
        String productName = selectedItem.getName();
        String brand = selectedItem.getBrand();
        Double price = selectedItem.getPrice();
        String description = selectedItem.getDescription();

        panel.add(new JLabel("Item: " + productName));
        panel.add(new JLabel("Brand: $" + brand));
        panel.add(new JLabel("Price: $" + price));
        panel.add(new JLabel("Description: " + description));

        // Check the type of the selected item and cast it accordingly
        if (selectedItem instanceof Locomotive) {
            Locomotive locomotive = (Locomotive) selectedItem;
            Gauge gauge = locomotive.getGauge();
            panel.add(new JLabel("Gauge: " + gauge));
            String era = locomotive.getEra();
            panel.add(new JLabel("Era: " + era));
        } else if (selectedItem instanceof Track) {
            Track track = (Track) selectedItem;
            Gauge gauge = track.getGauge();
            panel.add(new JLabel("Gauge: " + gauge));
        } else if (selectedItem instanceof Carriage) {
            Carriage carriage = (Carriage) selectedItem;
            Gauge gauge = carriage.getGauge();
            panel.add(new JLabel("Gauge: " + gauge));
            String era = carriage.getEra();
            panel.add(new JLabel("Era: " + era));
        }

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


